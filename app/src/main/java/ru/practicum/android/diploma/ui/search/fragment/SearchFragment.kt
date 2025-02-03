package ru.practicum.android.diploma.ui.search.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.common.SearchResult
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel
import ru.practicum.android.diploma.ui.vacancydetails.fragment.VacancyFragment
import ru.practicum.android.diploma.util.coroutine.CoroutineUtils

class SearchFragment : Fragment() {

    enum class PlaceholderState {
        CONTENT, NO_CONNECTION, LOADING, PAGINATION_LOADING, NOT_FOUND, EMPTY
    }

    private val binding: FragmentSearchBinding by lazy { FragmentSearchBinding.inflate(layoutInflater) }

    private val viewModel: SearchViewModel by viewModel()

    private var vacancyAdapter: VacancyAdapter? = null

    private var searchText: String = TEXT_VALUE

    private var vacanciesCount: Int = 0

    override fun onResume() {
        super.onResume()
        CoroutineUtils.debounceJob?.cancel()

        viewModel.setNewFilterParameters()
        viewModel.refreshSearchQuery(searchText)
        viewModel.isFilterOn()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INPUT_TEXT, TEXT_VALUE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // восстанавливаем сохраненное значение
        if (savedInstanceState != null) {
            searchText = savedInstanceState.getString(
                INPUT_TEXT,
                TEXT_VALUE
            )
        }

        val owner = getViewLifecycleOwner()

        val onSearchTrackClick: (Long) -> Unit =
            { vacancyId: Long -> viewModel.onVacancyClicked(vacancyId) }
        vacancyAdapter = VacancyAdapter(onSearchTrackClick)
        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewSearch.adapter = vacancyAdapter

        viewModel.getVacancyTrigger().observe(owner) { vacancyId ->
            openVacancyDetails(vacancyId)
        }

        viewModel.searchResultLiveData()
            .observe(owner) { searchResult: SearchResult -> renderSearchResult(searchResult) }

        viewModel.filterIconLiveData.observe(owner) {
            isFilterOn(it)
        }

        // нажатие на кнопку Done на клавиатуре просто скрывает ее
        binding.editTextSearch.setOnEditorActionListener { _, _, _ ->
            false
        }

        binding.recyclerViewSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val pos = (binding.recyclerViewSearch.layoutManager as LinearLayoutManager)
                        .findLastVisibleItemPosition()
                    val itemsCount = vacancyAdapter?.itemCount
                    if (itemsCount != null && pos >= itemsCount - 1) {
                        viewModel.onLastItemReached(binding.editTextSearch.text.toString())
                    }
                }
            }
        })

        // очистить строку поиска
        binding.clearIcon.setOnClickListener {
            clearSearchText()
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearIcon.isVisible = clearButtonIsVisible(s)
                binding.searchIcon.isVisible = !clearButtonIsVisible(s)
                if (!s.isNullOrEmpty()) {
                    CoroutineUtils.debounce(lifecycleScope, SEARCH_DEBOUNCE_DELAY, { enterSearch() })
                }
            }

            override fun afterTextChanged(s: Editable?) {
                searchText = binding.editTextSearch.text.toString()
                if (s.isNullOrEmpty()) {
                    CoroutineUtils.debounceJob?.cancel()
                }
            }
        }
        binding.editTextSearch.addTextChangedListener(simpleTextWatcher)

        binding.buttonFilter.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterCommonFragment)
        }
    }

    private fun clearSearchText() {
        binding.editTextSearch.setText("")
        viewModel.clearSearchResults()
        val view: View? = requireActivity().currentFocus

        if (view != null) {
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
        binding.editTextSearch.clearFocus()
    }

    // перейти на экран деаталей вакансии
    private fun openVacancyDetails(vacancyId: Long) {
        findNavController().navigate(
            R.id.action_searchFragment_to_vacancyFragment,
            VacancyFragment.createArgs(vacancyId, false)
        )
    }

    private fun renderSearchResult(result: SearchResult) {
        when (result) {
            is SearchResult.SearchVacanciesContent -> showPlaceholders(PlaceholderState.CONTENT)
            is SearchResult.NoConnection -> showPlaceholders(PlaceholderState.NO_CONNECTION)
            is SearchResult.Loading -> showPlaceholders(PlaceholderState.LOADING)
            is SearchResult.PaginationLoading -> showPlaceholders(PlaceholderState.PAGINATION_LOADING)
            is SearchResult.NotFound -> showPlaceholders(PlaceholderState.NOT_FOUND)
            is SearchResult.Empty -> showPlaceholders(PlaceholderState.EMPTY)
            else -> {}
        }

        when (result) {
            is SearchResult.SearchVacanciesContent -> showProgressBars(PlaceholderState.CONTENT)
            is SearchResult.NoConnection -> showProgressBars(PlaceholderState.NO_CONNECTION)
            is SearchResult.Loading -> showProgressBars(PlaceholderState.LOADING)
            is SearchResult.PaginationLoading -> showProgressBars(PlaceholderState.PAGINATION_LOADING)
            is SearchResult.NotFound -> showProgressBars(PlaceholderState.NOT_FOUND)
            is SearchResult.Empty -> showProgressBars(PlaceholderState.EMPTY)
            else -> {}
        }

        when (result) {
            is SearchResult.SearchVacanciesContent -> showContent(PlaceholderState.CONTENT, result)
            is SearchResult.NoConnection -> showContent(PlaceholderState.NO_CONNECTION, result)
            is SearchResult.Loading -> showContent(PlaceholderState.LOADING, result)
            is SearchResult.PaginationLoading -> showContent(PlaceholderState.PAGINATION_LOADING, result)
            is SearchResult.NotFound -> showContent(PlaceholderState.NOT_FOUND, result)
            is SearchResult.Empty -> showContent(PlaceholderState.EMPTY, result)
            else -> {}
        }
    }

    private fun showPlaceholders(state: PlaceholderState) {
        binding.noConnectionPlaceholderGroup.isVisible = state == PlaceholderState.NO_CONNECTION
        binding.notFoundPlaceholderGroup.isVisible = state == PlaceholderState.NOT_FOUND
        binding.searchDefaultPlaceholderImage.isVisible = state == PlaceholderState.EMPTY
    }

    private fun showProgressBars(state: PlaceholderState) {
        binding.progressBarSearch.isVisible = state == PlaceholderState.LOADING
        binding.progressBarPagination.isVisible = state == PlaceholderState.PAGINATION_LOADING
    }

    private fun showContent(state: PlaceholderState, result: SearchResult) {
        val textView = binding.textViewVacancies
        val recyclerViewSearch = binding.recyclerViewSearch

        when (result) {
            is SearchResult.SearchVacanciesContent -> {
                vacancyAdapter?.submitList(result.items)
                textView.text = getString(R.string.vacancy_count, result.found)
                vacanciesCount = result.found
            }

            is SearchResult.PaginationLoading -> {
                textView.text = getString(R.string.vacancy_count, vacanciesCount)
            }

            is SearchResult.NoConnection -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_connection_toast),
                    Toast.LENGTH_LONG
                ).show()
            }

            is SearchResult.Error -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.search_error_toast),
                    Toast.LENGTH_LONG
                ).show()
            }

            else -> {
                textView.text = getString(R.string.not_found_search_list)
            }
        }

        recyclerViewSearch.isVisible =
            state == PlaceholderState.CONTENT || state == PlaceholderState.PAGINATION_LOADING

        textView.isVisible = when (state) {
            PlaceholderState.CONTENT, PlaceholderState.NOT_FOUND, PlaceholderState.PAGINATION_LOADING -> true
            else -> false
        }

    }

    private fun enterSearch() {
        viewModel.searchVacancies(binding.editTextSearch.text.toString(), true)
    }

    private fun clearButtonIsVisible(s: CharSequence?): Boolean {
        return !s.isNullOrEmpty()
    }

    private fun isFilterOn(isFilterOn: Boolean) {
        if (isFilterOn) {
            binding.buttonFilter.setImageResource(R.drawable.ic_filter_on_24)
        } else {
            binding.buttonFilter.setImageResource(R.drawable.ic_filter_off_24)
        }
    }

    companion object {
        const val INPUT_TEXT = "INPUT_TEXT"
        const val TEXT_VALUE = ""
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
