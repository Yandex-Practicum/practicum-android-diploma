package ru.practicum.android.diploma.search.presenter.search

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.search.presenter.model.SearchState
import ru.practicum.android.diploma.search.presenter.model.VacancyPreviewUi
import ru.practicum.android.diploma.util.Debouncer
import ru.practicum.android.diploma.util.VacancyFormatter

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        VacanciesAdapter(requireContext(), mutableListOf(), ::onVacancyClick)
    }
    private val recyclerView: RecyclerView get() = binding.vacanciesRvId
    private val searchViewModel: SearchViewModel by viewModel()

    private val connectivityManager by lazy {
        requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    private var debouncer: Debouncer? = null
    private var lastAppliedFilters: Map<String, String> = emptyMap()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            activity?.runOnUiThread {
                searchViewModel.onInternetAppeared()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        debouncer = get { parametersOf(viewLifecycleOwner.lifecycleScope) }
        initRv()
        setupTextWatcher()
        clearEditText()
        goToFilters()
        stataObserver()
        setupScrollListener()
        loadFiltersFromStorage()
        observeFilterState()
        setupFragmentResultListener()
    }

    override fun onStart() {
        super.onStart()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onStop() {
        super.onStop()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        debouncer = null
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        searchViewModel.getFiltersState()
        if (binding.editTextId.text.isNullOrBlank()) {
            showEmpty()
        }
    }

    private fun loadFiltersFromStorage() {
        val (industry, salary, onlyWithSalary) = searchViewModel.loadFiltersFromStorage()
        val filters = mutableMapOf<String, String>()

        industry?.let {
            filters["industry"] = it.id
        }

        salary?.let {
            if (it.isNotBlank()) {
                filters["salary"] = it
            }
        }

        if (onlyWithSalary) {
            filters["only_with_salary"] = "true"
        }
        lastAppliedFilters = filters
    }

    private fun onVacancyClick(vacancyId: Int) {
        if (debouncer?.clickDebounce() ?: false) {
            findNavController().navigate(
                R.id.action_mainFragment_to_vacancyFragment,
                bundleOf("vacancyId" to vacancyId.toString())
            )
        }
    }

    private fun initRv() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun updateSearchIcon(text: CharSequence?) {
        if (text.isNullOrBlank()) {
            binding.searchIcon.setImageResource(R.drawable.search_24px)
            binding.searchIcon.tag = R.drawable.search_24px
        } else {
            binding.searchIcon.setImageResource(R.drawable.cross_light)
            binding.searchIcon.tag = R.drawable.cross_light
        }
    }

    private fun clearEditText() {
        binding.searchIcon.setOnClickListener {
            if (binding.searchIcon.tag == R.drawable.cross_light) {
                binding.editTextId.text.clear()
                debouncer?.cancelDebounce()
                showEmpty()
            }
        }
    }

    private fun goToFilters() {
        binding.filterButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_filtersFragment)
        }
    }

    private fun setupTextWatcher() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSearchIcon(s)
                if (!s.isNullOrBlank()) {
                    debouncer?.searchDebounce {
                        searchViewModel.searchVacancies(s.toString(), lastAppliedFilters)
                    }
                } else {
                    showEmpty()
                    debouncer?.cancelDebounce()
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
        }
        binding.editTextId.addTextChangedListener(textWatcher)
    }

    private fun setupScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val pos = layoutManager.findLastVisibleItemPosition()
                val itemsCount = adapter.itemCount
                if (pos >= itemsCount - 1) {
                    recyclerView.post {
                        searchViewModel.updatePage()
                    }
                }
            }
        })
    }

    private fun stataObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.state.collect { state ->
                    when (state) {
                        is SearchState.Loading -> showProgressBar()
                        is SearchState.Content -> showContent(state.data)
                        is SearchState.NotFound -> showNotFound()
                        is SearchState.NoInternet -> {
                            if (adapter.itemCount > 0) {
                                showMessage()
                            } else {
                                showNoInternet()
                            }
                        }

                        is SearchState.Error -> showError()
                        is SearchState.Empty -> showEmpty()
                        is SearchState.LoadingMore -> {
                            adapter.showLoading()
                            binding.progressBarId.visibility = View.GONE
                            binding.searchPreviewId.visibility = View.GONE
                            binding.notFoundPreview.visibility = View.GONE
                            binding.vacanciesRvId.visibility = View.VISIBLE
                            binding.infoShieldId.visibility = View.VISIBLE
                            binding.noInternetPreviewId.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun showMessage() {
        Toast.makeText(
            requireContext(),
            getString(R.string.no_internet_message),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showContent(data: List<VacancyPreviewUi>) {
        adapter.setList(data)
        adapter.hideLoading()
        binding.progressBarId.visibility = View.GONE
        binding.searchPreviewId.visibility = View.GONE
        binding.noInternetPreviewId.visibility = View.GONE
        binding.notFoundPreview.visibility = View.GONE
        binding.vacanciesRvId.visibility = View.VISIBLE
        binding.infoShieldId.visibility = View.VISIBLE
        binding.infoShieldId.text = "Найдено ${VacancyFormatter.changeEnding(data[0].found)}"
    }

    private fun showProgressBar() {
        binding.progressBarId.visibility = View.VISIBLE
        binding.searchPreviewId.visibility = View.GONE
        binding.notFoundPreview.visibility = View.GONE
        binding.vacanciesRvId.visibility = View.GONE
        binding.infoShieldId.visibility = View.GONE
        binding.noInternetPreviewId.visibility = View.GONE
        adapter.hideLoading()
    }

    private fun showNotFound() {
        binding.progressBarId.visibility = View.GONE
        binding.searchPreviewId.visibility = View.GONE
        binding.noInternetPreviewId.visibility = View.GONE
        binding.notFoundPreview.visibility = View.VISIBLE
        binding.vacanciesRvId.visibility = View.GONE
        binding.infoShieldId.visibility = View.VISIBLE
        binding.infoShieldId.text = "Таких вакансий нет"
        adapter.hideLoading()
    }

    private fun showNoInternet() {
        binding.progressBarId.visibility = View.GONE
        binding.searchPreviewId.visibility = View.GONE
        binding.noInternetPreviewId.visibility = View.VISIBLE
        binding.notFoundPreview.visibility = View.GONE
        binding.vacanciesRvId.visibility = View.GONE
        binding.infoShieldId.visibility = View.GONE
        adapter.hideLoading()
    }

    private fun showError() {
        binding.progressBarId.visibility = View.GONE
        binding.searchPreviewId.visibility = View.GONE
        binding.noInternetPreviewId.visibility = View.GONE
        binding.notFoundPreview.visibility = View.VISIBLE
        binding.vacanciesRvId.visibility = View.GONE
        binding.infoShieldId.visibility = View.GONE
        adapter.hideLoading()
    }

    private fun showEmpty() {
        binding.progressBarId.visibility = View.GONE
        binding.searchPreviewId.visibility = View.VISIBLE
        binding.noInternetPreviewId.visibility = View.GONE
        binding.notFoundPreview.visibility = View.GONE
        binding.vacanciesRvId.visibility = View.GONE
        binding.infoShieldId.visibility = View.GONE
        adapter.hideLoading()
    }

    private fun observeFilterState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.filterState.collect { filterState ->
                    when (filterState) {
                        FilterState.Empty -> {
                            binding.filterButton.setImageResource(R.drawable.filter_off)
                        }

                        FilterState.Saved -> {
                            binding.filterButton.setImageResource(R.drawable.filter_on)
                        }
                    }
                }
            }
        }
    }

    private fun setupFragmentResultListener() {
        setFragmentResultListener(
            getString(R.string.filter_request)
        ) { _, bundle ->
            val filtersApplied = bundle.getBoolean(
                getString(R.string.filters_applied),
                false
            )
            loadFiltersFromStorage()
            searchViewModel.getFiltersState()

            if (filtersApplied) {
                val currentQuery = binding.editTextId.text.toString()
                if (currentQuery.isNotBlank()) {
                    searchViewModel.searchVacancies(currentQuery, lastAppliedFilters)
                    debouncer?.cancelDebounce()
                } else {
                    showEmpty()
                }
            } else {
                debouncer?.cancelDebounce()
            }
        }
    }
}
