package ru.practicum.android.diploma.favorites_vacancies.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R

class FavoritesVacanciesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                FavoritesVacanciesScreen(
                    onOpenVacancyDetails = { openVacancyDetails() }
                )
            }
        }
    }

    private fun openVacancyDetails() {
        findNavController().navigate(R.id.vacancyDetailsFragment)
    }
}
