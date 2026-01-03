package ru.practicum.android.diploma.search.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.ui.theme.VacancySearchAppTheme
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchState
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchTextFieldState

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                VacancySearchAppTheme {
                    SearchScreen(
                        onOpenVacancyDetails = { openVacancyDetails() },
                        onOpenFilters = { openFilters() },
                        SearchState.Loading,
                        textFieldState = SearchTextFieldState()
                    )
                }
            }
        }
    }

    private fun openVacancyDetails() {
        findNavController().navigate(R.id.vacancyDetailsFragment)
    }

    private fun openFilters() {
        findNavController().navigate(R.id.searchFiltersFragment)
    }
}
