package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.ShortVacancy
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.presentation.SearchState
import ru.practicum.android.diploma.search.presentation.SearchStatus
import ru.practicum.android.diploma.search.presentation.SearchViewModel

class SearchFragment : Fragment() {
    private lateinit var vacancyAdapter: VacancyAdapter
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
        vacancyAdapter = VacancyAdapter()
        vacancyAdapter.onItemClick = {

        }
    }

    private fun render(it: SearchState) {
        when (it) {
            is SearchState.Default -> setStatus(SearchStatus.DEFAULT)
            is SearchState.Loading -> setStatus(SearchStatus.PROGRESS)
            is SearchState.Content -> showContent(it.data)
            is SearchState.NetworkError -> showNetworkError()
            is SearchState.EmptyResult -> showEmptyResult()
        }
    }

    private fun showContent(shortVacancy: List<ShortVacancy>) {
        setStatus(SearchStatus.SUCCESS)
        vacancyAdapter.vacancyList.clear()
        vacancyAdapter.vacancyList.addAll(shortVacancy)
        vacancyAdapter.notifyDataSetChanged()
    }

    private fun showNetworkError() {
        binding.errorPlaceholder.setImageResource(R.drawable.placeholder_no_internet)
        binding.placeholderText.setText(R.string.placeholder_no_internet)
        setStatus(SearchStatus.ERROR)
        vacancyAdapter.vacancyList.clear()
        vacancyAdapter.notifyDataSetChanged()
    }

    private fun showEmptyResult() {
        binding.errorPlaceholder.setImageResource(R.drawable.placeholder_nothing_found)
        binding.placeholderText.setText(R.string.placeholder_cannot_get_list_of_vacancy)
        setStatus(SearchStatus.ERROR)
        vacancyAdapter.vacancyList.clear()
        vacancyAdapter.notifyDataSetChanged()
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
                binding.tvVacancyAmount.visibility = View.GONE
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


}
