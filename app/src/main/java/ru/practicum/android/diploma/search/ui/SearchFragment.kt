package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.presentation.SearchViewModel
import ru.practicum.android.diploma.search.presentation.models.UiScreenState
import ru.practicum.android.diploma.search.presentation.models.VacancyUi

class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private val viewModel by viewModel<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.buttonFilters?.setOnClickListener {
            findNavController().navigate(
                R.id.action_searchFragment_to_filterFragment
            )
        }

        binding?.buttonVacancies?.setOnClickListener {
            findNavController().navigate(
                R.id.action_searchFragment_to_vacanciesFragment
            )
        }

        viewModel.uiState.observe(viewLifecycleOwner) {
            renderUiState(it)
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

    @Suppress("EmptyBlock")
    private fun showDefaultState() {
        // Implement this method later
    }

    @Suppress("EmptyBlock")
    private fun showEmptyState() {
        // Implement this method later
    }

    @Suppress("EmptyBlock")
    private fun showLoadingState() {
        // Implement this method later
    }

    @Suppress("EmptyBlock")
    private fun showNoInternetErrorState() {
        // Implement this method later
    }

    @Suppress("EmptyBlock")
    private fun showServerErrorState() {
        // Implement this method later
    }

    @Suppress("EmptyBlock")
    private fun showSuccessState(vacancies: List<VacancyUi>, found: Int) {
        // Implement this method later
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
