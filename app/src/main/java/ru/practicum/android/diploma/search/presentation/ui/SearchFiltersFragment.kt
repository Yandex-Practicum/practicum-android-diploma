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

class SearchFiltersFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    SearchFiltersScreen(
                        onBack = { navigateBack() },
                        onOpenIndustryFilter = { openIndustryFilter() },
                    )
                }
            }
        }
    }

    private fun openIndustryFilter() {
        findNavController().navigate(R.id.searchIndustryFilterFragment)
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }
}
