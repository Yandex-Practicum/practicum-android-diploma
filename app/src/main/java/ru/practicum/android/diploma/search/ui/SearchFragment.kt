package ru.practicum.android.diploma.search.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.SearchFilterParameters
import ru.practicum.android.diploma.core.domain.model.SearchVacanciesResult
import ru.practicum.android.diploma.core.domain.model.ShortVacancy
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.usecase.SearchVacancyUseCase
import ru.practicum.android.diploma.search.presentation.SearchState
import ru.practicum.android.diploma.search.presentation.SearchStatus
import ru.practicum.android.diploma.search.presentation.SearchViewModel

class SearchFragment : Fragment() {
    private val mockedParameters = SearchFilterParameters()
    private val viewModel by viewModel<SearchViewModel>()
    private var vacancyAdapter: VacancyAdapter = VacancyAdapter { vacancy ->
        transitionToDetailedVacancy(vacancy.id)
    }
    private var isLastPageReached = false
    private var onScrollLister: RecyclerView.OnScrollListener? = null
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var vacancyAmount: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
        setupListeners()
    }

    private fun setupListeners() {
        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.searchByText(binding.searchEditText.text.toString(), mockedParameters)
            if (text.isNullOrEmpty()) {
                binding.icSearchOrCross.setImageResource(R.drawable.ic_search)
            } else {
                binding.icSearchOrCross.setImageResource(R.drawable.ic_close)
            }
        }
        binding.icSearchOrCross.setOnClickListener {
            binding.searchEditText.text = null
            viewModel.clearSearch()
        }
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchByButton(binding.searchEditText.text.toString(), mockedParameters)
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
            }
            true
        }
        binding.vacancyRecycler.adapter = vacancyAdapter
        binding.vacancyRecycler.layoutManager = LinearLayoutManager(requireContext())
        setPaginationListener()
        binding.searchToolbar.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterFragment)
        }
    }

    private fun setPaginationListener() {
        onScrollLister = object : RecyclerView.OnScrollListener() {
            private var lastVisibleItem = -1

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isLastPageReached) {
                    binding.paginationProgressBar.visibility = View.GONE
                    return
                }
                val currentLastVisibleItem = (recyclerView.layoutManager as LinearLayoutManager)
                    .findLastVisibleItemPosition()
                if (isTimeToGetNextPage(currentLastVisibleItem) && currentLastVisibleItem != lastVisibleItem) {
                    if (isScrolledToLastItem(currentLastVisibleItem)) {
                        binding.paginationProgressBar.show()
                    } else {
                        binding.paginationProgressBar.visibility = View.GONE
                    }
                    viewModel.searchByPage(
                        searchText = binding.searchEditText.text.toString(),
                        page = getNextPageIndex(),
                        filterParameters = mockedParameters
                    )
                } else if (currentLastVisibleItem != lastVisibleItem) {
                    binding.paginationProgressBar.visibility = View.GONE
                }
                lastVisibleItem = currentLastVisibleItem
            }
        }
        onScrollLister?.let { binding.vacancyRecycler.addOnScrollListener(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onScrollLister?.let { binding.vacancyRecycler.removeOnScrollListener(it) }
    }

    private fun isTimeToGetNextPage(lastVisibleItem: Int): Boolean {
        return lastVisibleItem > vacancyAdapter.itemCount - PRE_PAGINATION_ITEM_COUNT
    }

    private fun getNextPageIndex(): Int {
        return vacancyAdapter.itemCount / SearchVacancyUseCase.DEFAULT_VACANCIES_PER_PAGE
    }

    private fun isScrolledToLastItem(lastVisibleItem: Int): Boolean {
        return lastVisibleItem + 1 >= vacancyAdapter.itemCount
    }

    private fun search() {
        if (binding.searchEditText.text.isNotEmpty()) {
            viewModel.searchByText(binding.searchEditText.text.toString(), mockedParameters)
        }
    }

    private fun render(it: SearchState) {
        when (it) {
            is SearchState.Default -> {
                vacancyAdapter.updateList(emptyList())
                setStatus(SearchStatus.DEFAULT)
            }

            is SearchState.Loading -> {
                vacancyAdapter.updateList(emptyList())
                setStatus(SearchStatus.PROGRESS)
            }

            is SearchState.Content -> showContent(it.data!!)

            is SearchState.Pagination -> {
                updateContent(it.data, it.error)
            }

            is SearchState.ServerError -> {
                showServerError()
            }

            is SearchState.NetworkError -> showNetworkError()
            is SearchState.EmptyResult -> showEmptyResult()
        }
    }

    private fun updateContent(vacancies: List<ShortVacancy>, error: SearchState?) {
        binding.paginationProgressBar.visibility = View.GONE
        if (vacancies.isNotEmpty()) {
            isLastPageReached = vacancies.size < SearchVacancyUseCase.DEFAULT_VACANCIES_PER_PAGE
            vacancyAdapter.pagination(vacancies)
            binding.tvVacancyAmount.text =
                requireContext().resources.getQuantityString(
                    R.plurals.vacancies,
                    vacancyAmount ?: 0,
                    vacancyAmount ?: 0
                )
        }
        if (error is SearchState.NetworkError) {
            showToast(getString(R.string.pagination_error_error_connection))
        } else if (error is SearchState.ServerError) {
            showToast(getString(R.string.pagination_error_server_error))
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    private fun showContent(searchVacanciesResult: SearchVacanciesResult) {
        isLastPageReached = searchVacanciesResult.vacancies.size < SearchVacancyUseCase.DEFAULT_VACANCIES_PER_PAGE
        vacancyAdapter.updateList(searchVacanciesResult.vacancies)
        binding.tvVacancyAmount.text =
            requireContext().resources.getQuantityString(
                R.plurals.vacancies,
                searchVacanciesResult.numOfResults,
                searchVacanciesResult.numOfResults
            )
        vacancyAmount = searchVacanciesResult.numOfResults
        setStatus(SearchStatus.SUCCESS)
    }

    private fun showNetworkError() {
        binding.errorPlaceholder.setImageResource(R.drawable.placeholder_no_internet)
        binding.placeholderText.setText(R.string.placeholder_no_internet)
        vacancyAdapter.updateList(emptyList())
        setStatus(SearchStatus.ERROR)
    }

    private fun showEmptyResult() {
        binding.errorPlaceholder.setImageResource(R.drawable.placeholder_nothing_found)
        binding.placeholderText.setText(R.string.placeholder_cannot_get_list_of_vacancy)
        vacancyAdapter.updateList(emptyList())
        setStatus(SearchStatus.ERROR)
    }

    private fun showServerError() {
        binding.errorPlaceholder.setImageResource(R.drawable.placeholder_server_error)
        binding.placeholderText.setText(R.string.placeholder_server_error)
        vacancyAdapter.updateList(emptyList())
        setStatus(SearchStatus.ERROR)
    }

    private fun setStatus(status: SearchStatus) {
        when (status) {
            SearchStatus.PROGRESS -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.placeholderSearch.visibility = View.GONE
                binding.vacancyRecycler.visibility = View.GONE
                binding.placeholderText.visibility = View.GONE
                binding.errorPlaceholder.visibility = View.GONE
                binding.tvVacancyAmount.visibility = View.GONE
            }

            SearchStatus.ERROR -> {
                binding.progressBar.visibility = View.GONE
                binding.placeholderSearch.visibility = View.GONE
                binding.vacancyRecycler.visibility = View.GONE
                binding.placeholderText.visibility = View.VISIBLE
                binding.errorPlaceholder.visibility = View.VISIBLE
                binding.tvVacancyAmount.visibility = View.GONE
            }

            SearchStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
                binding.placeholderSearch.visibility = View.GONE
                binding.vacancyRecycler.visibility = View.VISIBLE
                binding.placeholderText.visibility = View.GONE
                binding.errorPlaceholder.visibility = View.GONE
                binding.tvVacancyAmount.visibility = View.VISIBLE
                binding.paginationProgressBar.visibility = View.GONE
            }

            SearchStatus.DEFAULT -> {
                binding.progressBar.visibility = View.GONE
                binding.placeholderSearch.visibility = View.VISIBLE
                binding.vacancyRecycler.visibility = View.GONE
                binding.placeholderText.visibility = View.GONE
                binding.errorPlaceholder.visibility = View.GONE
                binding.tvVacancyAmount.visibility = View.GONE
            }
        }
    }

    private fun transitionToDetailedVacancy(vacancyId: Long) {
        if (viewModel.clickDebounce()) {
            val action = SearchFragmentDirections.actionSearchFragmentToVacancyFragment(vacancyId)
            findNavController().navigate(action)
        }
    }

    companion object {
        private const val PRE_PAGINATION_ITEM_COUNT = 5
    }
}
