package ru.practicum.android.diploma.search.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.presentation.SearchAdapter
import ru.practicum.android.diploma.common.presentation.decorations.LayoutItemDecoration
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.model.AdapterState
import ru.practicum.android.diploma.search.domain.model.SearchViewState
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchViewModel

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SearchViewModel>()
    private var adapter: SearchAdapter? = null
    private val handler: Handler = Handler(Looper.getMainLooper())
    private var editTextWatcher: TextWatcher? = null
    private var hideAdapterLoadingRunnable = Runnable {
        adapter?.hideLoading()
    }
    private var job: Job? = null
    private var searchVacanciesRunnable = Runnable {
        searchOnTextChanged(textInput)
    }
    private var textInput: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        adapter = null
        handler.removeCallbacksAndMessages(this.hideAdapterLoadingRunnable)
        editTextWatcher?.let { binding.textInput.removeTextChangedListener(it) }
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filterButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterSettingsFragment)
        }

        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

        binding.initScreenPH.isVisible = true
        adapter = SearchAdapter(viewModel::showVacancyDetails)
        binding.searchVacanciesRV.adapter = adapter
        val itemDecoration = LayoutItemDecoration(FIRST_ITEM_MARGIN_TOP).apply {
            init(requireContext())
        }
        binding.searchVacanciesRV.addItemDecoration(itemDecoration)

        binding.clearIcon.setOnClickListener {
            onClearIconPressed()
            inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
        }

        binding.textInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchVacancy(textInput)
                true
            }
            false
        }

        editTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, ncout: Int) {
                with(binding) {
                    if (textInput.hasFocus() && s?.isEmpty() == true) {
                        initScreenPH.isVisible = true
                        textHint.isVisible = false
                        searchVacanciesRV.isVisible = false
                        noVacanciesFoundPH.isVisible = false
                        noConnectionPH.isVisible = false
                    }
                }
                textInput = s.toString()
                Log.d("TextInput", "$textInput")
                binding.clearIcon.visibility = clearButtonVisibility(s)
                binding.searchIcon.visibility = searchIconVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("AfterTextChanged", "$s")
                searchOnTextChanged(textInput)
            }
        }
        binding.textInput.addTextChangedListener(editTextWatcher)

        binding.searchVacanciesRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("OnScroll", "scrollEvent")
                if (dy > 0) {
                    val pos =
                        (binding.searchVacanciesRV.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = adapter!!.itemCount
                    if (pos >= itemsCount - 1) {
                        handler.postDelayed(
                            { loadNextPage() },
                            DELAY_500
                        )
//                        loadNextPage()
                    }
                }
            }
        })

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            Log.d("state", "$state")
            render(state)
        }

        viewModel.getAdapterStateLiveData().observe(viewLifecycleOwner) { adapterState ->
            Log.d("adapterState", "$adapterState")
            renderAdapterState(adapterState)
        }

        viewModel.getShowVacancyDetails().observe(viewLifecycleOwner) { vacancyId ->
            showVacancyDetails(vacancyId)
        }
    }

    private fun renderAdapterState(state: AdapterState) {
        Log.d("AdapterState", "$state")
        when (state) {
            is AdapterState.IsLoading -> adapter?.showLoading()
            is AdapterState.Idle -> hideAdapterLoading()
        }
    }

    private fun render(state: SearchViewState) {
        when (state) {
            is SearchViewState.Content -> showContent(state)
            is SearchViewState.Loading -> showMainProgressBar()
            is SearchViewState.ConnectionError -> showNoConnectionPH()
            is SearchViewState.NotFoundError -> showNoVacanciesFoundPH()
            is SearchViewState.ServerError -> showServerErrorPH()
            else -> {}
        }
    }

    private fun showNoVacanciesFoundPH() {
        with(binding) {
            textHint.text = NO_VACANCIES_FOUND
            textHint.isVisible = true
            noVacanciesFoundPH.isVisible = true
            initScreenPH.isVisible = false
            serverErrorPH.isVisible = false
            noConnectionPH.isVisible = false
            mainProgressBar.isVisible = false
            searchVacanciesRV.isVisible = false
        }
    }

    private fun showServerErrorPH() {
        with(binding) {
            serverErrorPH.isVisible = true
            initScreenPH.isVisible = false
            textHint.isVisible = false
            noConnectionPH.isVisible = false
            mainProgressBar.isVisible = false
            searchVacanciesRV.isVisible = false
            noVacanciesFoundPH.isVisible = false
        }
    }

    private fun showNoConnectionPH() {
        when {
            adapter?.currentList.isNullOrEmpty() -> {
                with(binding) {
                    noConnectionPH.isVisible = true
                    noConnectionTextHint.isVisible = true
                    initScreenPH.isVisible = false
                    textHint.isVisible = false
                    mainProgressBar.isVisible = false
                    searchVacanciesRV.isVisible = false
                    noVacanciesFoundPH.isVisible = false
                    serverErrorPH.isVisible = false
                }
            }

            job?.isActive == true -> return
            job?.isActive != true -> {
                job = CoroutineScope(Dispatchers.Main).launch {
                    hideAdapterLoading()
                    Toast.makeText(requireContext(), "Проверьте подключение", Toast.LENGTH_LONG).show()
                    delay(DELAY_2000)
                }
            }

//        if (adapter?.currentList.isNullOrEmpty()) {
//            with(binding) {
//                noConnectionPH.isVisible = true
//                noConnectionTextHint.isVisible = true
//                initScreenPH.isVisible = false
//                textHint.isVisible = false
//                mainProgressBar.isVisible = false
//                searchVacanciesRV.isVisible = false
//                noVacanciesFoundPH.isVisible = false
//                serverErrorPH.isVisible = false
//            }
//        } else {
//            if (job?.isActive == true) return
//            job = CoroutineScope(Dispatchers.Main).launch {
//                hideAdapterLoading()
//                Toast.makeText(requireContext(), "Проверьте подключение", Toast.LENGTH_LONG).show()
//                delay(DELAY_2000)
//            }
//            if(!job.isCompleted) {
//                return
//            } else{
//                job.start()
//            }
//            CoroutineScope(Dispatchers.Main).launch {
//                hideAdapterLoading()
//                Toast.makeText(requireContext(), "Проверьте подключение", Toast.LENGTH_LONG).show()
//                delay(2000)
//            }
//            hideAdapterLoading()
//            Toast.makeText(requireContext(), "Проверьте подключение", Toast.LENGTH_LONG).show()
        }
    }

    private fun showMainProgressBar() {
        with(binding) {
            mainProgressBar.isVisible = true
            initScreenPH.isVisible = false
            textHint.isVisible = false
            searchVacanciesRV.isVisible = false
            noVacanciesFoundPH.isVisible = false
            noConnectionPH.isVisible = false
            serverErrorPH.isVisible = false
        }
    }

    private fun hideAdapterLoading() {
        handler.postDelayed(
            hideAdapterLoadingRunnable,
            DELAY_1000
        )
    }

    private fun loadNextPage() {
        viewModel.onLastItemReached(textInput)
    }

    private fun searchOnTextChanged(query: String) {
        with(binding) {
            textHint.isVisible = false
            noConnectionTextHint.isVisible = false
            noVacanciesFoundPH.isVisible = false
            serverErrorPH.isVisible = false
        }
        viewModel.searchDebounce(query)
        adapter?.submitList(emptyList())
    }

    private fun onClearIconPressed() {
        with(binding) {
            viewModel.declineLastSearch()
            textInput.setText("")
            adapter?.submitList(emptyList())
            initScreenPH.isVisible = true
            searchVacanciesRV.isVisible = false
            textHint.isVisible = false
            noConnectionPH.isVisible = false
            serverErrorPH.isVisible = false
            mainProgressBar.isVisible = false
        }
    }

    private fun showContent(state: SearchViewState.Content) {
        with(binding) {
            if (job?.isActive == true) {
                job?.cancel()
            }
            adapter?.submitData(state.listItem)
            textHint.text = state.vacanciesFoundHint
            textHint.isVisible = true
            searchVacanciesRV.isVisible = true
            mainProgressBar.isVisible = false
            noVacanciesFoundPH.isVisible = false
            noConnectionPH.isVisible = false
            initScreenPH.isVisible = false
        }
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun searchIconVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun showVacancyDetails(vacancyId: String) {
        findNavController().navigate(
            R.id.action_searchFragment_to_vacancyFragment,
        )
    }

    companion object {
        private const val FIRST_ITEM_MARGIN_TOP = 46
        private const val DELAY_2000 = 2_000L
        private const val DELAY_1000 = 1_000L
        private const val DELAY_500 = 500L
        private const val NO_VACANCIES_FOUND = "Таких вакансий нет"
    }
}
