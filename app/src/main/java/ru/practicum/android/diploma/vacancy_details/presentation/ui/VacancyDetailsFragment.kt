package ru.practicum.android.diploma.vacancy_details.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

class VacancyDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                VacancyDetailsScreen(
                    onBack = { navigateBack() }
                )
            }
        }
    }

    private fun navigateBack() {
        parentFragmentManager.popBackStack()
    }

    companion object {
        fun newInstance() : VacancyDetailsFragment = VacancyDetailsFragment()
    }
}
