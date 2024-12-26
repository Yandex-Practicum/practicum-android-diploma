package ru.practicum.android.diploma.ui.search.activity

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.search.models.SearchParams
import ru.practicum.android.diploma.domain.search.models.SearchScreenState
import ru.practicum.android.diploma.ui.favorites.VacancyAdapter
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private var previousTextInEditText: String = ""
    private var searchJob: Job? = null
    private var inputEditText: EditText? = null
    private var foundedVacanciesRecyclerView: RecyclerView? = null
    private var foundedVacanciesRecyclerViewAdapter: VacancyAdapter? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        inputEditText = binding.etSearchVacancy
        val clearButton = binding.ibClearQuery
        foundedVacanciesRecyclerView = binding.rvFoundedVacancies

        setupObserversState()

        inputEditText?.addTextChangedListener { s ->
            clearButton.isVisible = !s.isNullOrEmpty()
            binding.ibClearSearch.isVisible = s.isNullOrEmpty()
            viewModel.searchJob.value?.cancel()
            if (!s.isNullOrBlank() && s.toString() != viewModel.previousTextInEditText.value) {
                hidePlaceholders()
                viewModel.updatePreviousTextInEditText(s.toString())

                viewModel.updateSearchJob(lifecycleScope.launch {
                    delay(SEARCH_REQUEST_DELAY_IN_MILLISEC)
                    val searchParams = SearchParams(
                        searchQuery = s.toString(),
                        numberOfPage = "0"
                    )
                    viewModel.searchVacancies(searchParams)
                })

                previousTextInEditText = s.toString()
                searchJob = lifecycleScope.launch {
                    delay(SEARCH_REQUEST_DELAY_IN_MILLISEC)
                    searchJobStart(s.toString())
                }
            }
        }

        clearButton.setOnClickListener {
            inputEditText.setText(CLEAR_TEXT)
            inputEditText.requestFocus()
            hideKeyboard(inputEditText)
            showEmpty()
            viewModel.resetSearchState()
            clear()
        }

        val onItemClickListener: (Vacancy) -> Unit = {
            val bundle = Bundle().apply {
                putString("vacancy_id", it.id)
            }
            findNavController().navigate(R.id.action_searchFragment_to_vacancyFragment, bundle)
        }
        val onItemLongClickListener: (Vacancy) -> Unit = {
            // Логика для выполнения по долгому нажатию на элемент
        }

        foundedVacanciesRecyclerViewAdapter = VacancyAdapter(
            onItemClicked = onItemClickListener,
            onLongItemClicked = onItemLongClickListener
        )

        foundedVacanciesRecyclerView?.adapter = foundedVacanciesRecyclerViewAdapter

        foundedVacanciesRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                positionUpdate(dy)
            }
        })

        binding.ivFilter.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterSettingsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun searchJobStart(s: String) {
        val searchParams = SearchParams(
            searchQuery = s,
            numberOfPage = "0"
        )
        viewModel.searchVacancies(searchParams)
    }

    private fun positionUpdate(dy: Int) {
        if (dy > 0) {
            val pos = (foundedVacanciesRecyclerView?.layoutManager as LinearLayoutManager)
                .findLastVisibleItemPosition()
            val itemsCount = foundedVacanciesRecyclerViewAdapter?.itemCount

            if (itemsCount != null && pos >= itemsCount - 1) {
                viewModel.onLastItemReached()
            }
        }
    }

    private fun clear() {
        inputEditText?.setText(CLEAR_TEXT)
        inputEditText?.requestFocus()
        inputEditText?.let { hideKeyboard(it) }
        showEmpty()
        viewModel.resetSearchState()
    }

    private fun setupObserversState() {
        viewModel.getSearchScreenStateLiveData().observe(viewLifecycleOwner) { state ->
            when (state) {
                is SearchScreenState.Loading -> showLoading()
                is SearchScreenState.Content -> showContent(state)
                is SearchScreenState.ServerError -> showServerError()
                is SearchScreenState.NetworkError -> showNetworkError()
                is SearchScreenState.NotFound -> showNotFound()
                is SearchScreenState.Error -> showServerError()
                is SearchScreenState.Empty -> showEmpty()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible =
                isLoading && !(viewModel.isPaginationLoading.value ?: false)
        }

        viewModel.isPaginationLoading.observe(viewLifecycleOwner) { isPaginationLoading ->
            binding.progressBarPagination.isVisible = isPaginationLoading
            if (isPaginationLoading) {
                binding.progressBar.isVisible = false
            }
        }

        viewModel.counterVacancy.observe(viewLifecycleOwner) { counter ->
            binding.vacancyCounter.text = viewModel.getVacanciesText(counter)
        }

        viewModel.observeShowToast().observe(viewLifecycleOwner) { message ->
            showToast(message)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            vacancyCounter.isVisible = false
            rvFoundedVacancies.isVisible = false
            hidePlaceholders()
        }
    }

    private fun showContent(state: SearchScreenState.Content) {
        with(binding) {
            progressBar.isVisible = false
            vacancyCounter.isVisible = true
            rvFoundedVacancies.isVisible = true
            hidePlaceholders()
        }
        val foundedVacanciesRecyclerView = binding.rvFoundedVacancies
        val adapter = foundedVacanciesRecyclerView.adapter as? VacancyAdapter
        adapter?.setData(state.foundedVacancies)
    }

    private fun showServerError() {
        with(binding) {
            progressBar.isVisible = false
            rvFoundedVacancies.isVisible = false
            serverErrorPlaceholder.isVisible = true
        }
    }

    private fun showNetworkError() {
        with(binding) {
            progressBar.isVisible = false
            vacancyCounter.isVisible = false
            rvFoundedVacancies.isVisible = false
            networkErrorPlaceholder.isVisible = true
        }
    }

    private fun showNotFound() {
        with(binding) {
            vacancyCounter.text = viewModel.getVacanciesText()
            progressBar.isVisible = false
            vacancyCounter.isVisible = true
            rvFoundedVacancies.isVisible = false
            notFoundPlaceholder.isVisible = true
        }
    }

    private fun showEmpty() {
        with(binding) {
            progressBar.isVisible = false
            vacancyCounter.isVisible = false
            rvFoundedVacancies.isVisible = false
            hidePlaceholders()
            placeholderMain.isVisible = true
        }
    }

    private fun hidePlaceholders() {
        with(binding) {
            notFoundPlaceholder.isVisible = false
            networkErrorPlaceholder.isVisible = false
            serverErrorPlaceholder.isVisible = false
            placeholderMain.isVisible = false
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        private const val CLEAR_TEXT = ""
        private const val SEARCH_REQUEST_DELAY_IN_MILLISEC = 2000L
    }
}
