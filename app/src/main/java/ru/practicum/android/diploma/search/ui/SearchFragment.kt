package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.SearchFilterParameters
import ru.practicum.android.diploma.core.domain.model.SearchVacanciesResult
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.presentation.SearchState
import ru.practicum.android.diploma.search.presentation.SearchStatus
import ru.practicum.android.diploma.search.presentation.SearchViewModel

class SearchFragment : Fragment() {
    private var vacancyAdapter: VacancyAdapter = VacancyAdapter()
    private val mockedParameters = SearchFilterParameters("", "", "", false)
    private val viewModel by viewModel<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        vacancyAdapter.onItemClick = {
            transitionToDetailedVacancy(it.id)
        }
        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
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
                search()
            }
            true
        }
        binding.vacancyRecycler.adapter = vacancyAdapter
        binding.vacancyRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun search() {
        if (binding.searchEditText.text.isNotEmpty()) {
            setStatus(SearchStatus.PROGRESS)
            viewModel.initSearch(binding.searchEditText.text.toString(), 0, mockedParameters)
        }
    }

    private fun render(it: SearchState) {
        when (it) {
            is SearchState.Default -> setStatus(SearchStatus.DEFAULT)
            is SearchState.Loading -> setStatus(SearchStatus.PROGRESS)
            is SearchState.Content -> showContent(it.data!!)
            is SearchState.NetworkError -> showNetworkError()
            is SearchState.EmptyResult -> showEmptyResult()
        }
    }

    private fun showContent(searchVacanciesResult: SearchVacanciesResult) {
        setStatus(SearchStatus.SUCCESS)
        vacancyAdapter?.vacancyList?.clear()
        vacancyAdapter?.vacancyList?.addAll(searchVacanciesResult.vacancies)
        vacancyAdapter?.notifyDataSetChanged()
        binding.tvVacancyAmount.text =
            requireContext().resources.getQuantityString(
                R.plurals.vacancies,
                searchVacanciesResult.numOfResults,
                searchVacanciesResult.numOfResults
            )
    }

    private fun showNetworkError() {
        binding.errorPlaceholder.setImageResource(R.drawable.placeholder_no_internet)
        binding.placeholderText.setText(R.string.placeholder_no_internet)
        setStatus(SearchStatus.ERROR)
        vacancyAdapter?.vacancyList?.clear()
        vacancyAdapter?.notifyDataSetChanged()
    }

    private fun showEmptyResult() {
        binding.errorPlaceholder.setImageResource(R.drawable.placeholder_nothing_found)
        binding.placeholderText.setText(R.string.placeholder_cannot_get_list_of_vacancy)
        setStatus(SearchStatus.ERROR)
        vacancyAdapter?.vacancyList?.clear()
        vacancyAdapter?.notifyDataSetChanged()
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
}
