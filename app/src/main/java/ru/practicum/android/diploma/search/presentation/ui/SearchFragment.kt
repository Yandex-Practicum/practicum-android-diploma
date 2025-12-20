package ru.practicum.android.diploma.search.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    SearchScreen(
                        onOpenVacancyDetails = { openVacancyDetails() },
                        onOpenFilters = { openFilters() },
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
