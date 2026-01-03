package ru.practicum.android.diploma.search.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.ui.theme.VacancySearchAppTheme
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    val viewModel by viewModel<SearchViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        return ComposeView(requireContext()).apply {
            setContent {
                VacancySearchAppTheme {
                    SearchScreen(
                        onOpenVacancyDetails = { id -> openVacancyDetails(id) },
                        onOpenFilters = { openFilters() },
                        viewModel
                    )
                }
            }
        }
    }


    private fun openVacancyDetails(vacancyId: String) {
        val args = bundleOf(
            "vacancyId" to vacancyId,
            "openedFromFavorites" to false,
        )
        findNavController().navigate(R.id.vacancyDetailsFragment, args)
    }


    private fun openFilters() {
        findNavController().navigate(R.id.searchFiltersFragment)
    }
}
