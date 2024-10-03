package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.presentation.SearchViewModel
import ru.practicum.android.diploma.search.presentation.models.UiScreenState
import ru.practicum.android.diploma.search.presentation.models.VacancyUi
import ru.practicum.android.diploma.util.debounce

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SearchViewModel>()
    private var searchItemAdapter: SearchItemAdapter? = null
    private val debounceSearch = debounce<String>(
        delayMs = 2000L,
        scope = lifecycleScope,
        action = { query ->
            viewModel.onSearchQueryChanged(query)
        }
    )

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

        viewModel.uiState.observe(viewLifecycleOwner) {
            renderUiState(it)
        }

        searchItemAdapter = SearchItemAdapter {
            val action = ""
        }

        binding.vacancyList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchItemAdapter
        }

        binding.editText.addTextChangedListener { query ->
            debounceSearch(query.toString())
        }
    }

    private fun renderUiState(state: UiScreenState) {
        when (state) {
            UiScreenState.Default -> showDefaultState()
            UiScreenState.Empty -> showEmptyState()
            UiScreenState.Loading -> showLoadingState()
            UiScreenState.NoInternetError -> showNoInternetErrorState()
            UiScreenState.ServerError -> showServerErrorState()
            is UiScreenState.Success -> showSuccessState(state.vacancies, state.found)
        }
    }

    private fun showDefaultState() {
        hideAllState()
        binding.ivImgSearch.visibility = View.VISIBLE
    }

    private fun showEmptyState() {
        hideAllState()
        binding.tvCountList.text = getString(R.string.no_vacancies_text)
        binding.tvCountList.visibility = View.VISIBLE
        binding.clEmptyList.visibility = View.VISIBLE
    }

    private fun showLoadingState() {
        hideAllState()
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun showNoInternetErrorState() {
        hideAllState()
        binding.clNoInternet.visibility = View.VISIBLE
    }

    @Suppress("EmptyBlock")
    private fun showServerErrorState() {
        // Implement this method later
    }

    private fun showSuccessState(vacancies: List<VacancyUi>, found: Int) {
        hideAllState()
        searchItemAdapter?.update(vacancies)
        binding.tvCountList.text = getString(R.string.found_vacancies_count, found)
        binding.tvCountList.visibility = View.VISIBLE
        binding.vacancyList.visibility = View.VISIBLE
    }

    private fun hideAllState() {
        binding.ivImgSearch.visibility = View.GONE
        binding.tvCountList.visibility = View.GONE
        binding.vacancyList.visibility = View.GONE
        binding.pbLoading.visibility = View.GONE
        binding.clEmptyList.visibility = View.GONE
        binding.clNoInternet.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
