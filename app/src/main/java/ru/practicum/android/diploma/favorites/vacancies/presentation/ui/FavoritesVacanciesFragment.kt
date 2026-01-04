package ru.practicum.android.diploma.favorites.vacancies.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.ui.theme.VacancySearchAppTheme

class FavoritesVacanciesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                VacancySearchAppTheme {
                    FavoritesVacanciesScreen(
                        onOpenVacancyDetails = { vacancyId -> openVacancyDetails(vacancyId) }
                    )
                }
            }
        }
    }

    private fun openVacancyDetails(vacancyId: String) {
        val args = bundleOf(
            "vacancyId" to vacancyId,
            "openedFromFavorites" to true,
            "isFavorite" to true,
        )
        findNavController().navigate(R.id.vacancyDetailsFragment, args)
    }
}
