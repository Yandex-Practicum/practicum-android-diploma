package ru.practicum.android.diploma.vacancy.details.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.vacancy.details.presentation.viewmodel.VacancyDetailsViewModel

class VacancyDetailsFragment : Fragment() {

    private val viewModel: VacancyDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val vacancy by viewModel.vacancy.collectAsState()
                val isFavorite by viewModel.isFavorite.collectAsState()

                VacancyDetailsScreen(
                    vacancy = vacancy,
                    isFavorite = isFavorite,
                    onBack = { navigateBack() },
                    onShare = { shareVacancy() },
                    onFavoriteClick = { viewModel.toggleFavorite() }
                )
            }
        }
    }

    private fun navigateBack() {
        parentFragmentManager.popBackStack()
    }

    private fun shareVacancy() {
        val url = viewModel.getShareUrl()

        if (url != null) {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, url)
            }
            val chooserIntent = Intent.createChooser(shareIntent, "Поделиться вакансией")
            startActivity(chooserIntent)
        }
    }

    companion object {
        fun newInstance(): VacancyDetailsFragment = VacancyDetailsFragment()
    }
}
