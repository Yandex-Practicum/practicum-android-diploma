package ru.practicum.android.diploma.vacancy.details.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.vacancy.details.domain.model.VacancyDetailsSource
import ru.practicum.android.diploma.vacancy.details.presentation.viewmodel.VacancyDetailsViewModel

class VacancyDetailsFragment : Fragment() {

    private val viewModel: VacancyDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Получаем аргументы из навигации
        val vacancyId = arguments?.getString("vacancyId")
        val openedFromFavorites = arguments?.getBoolean("openedFromFavorites", false) ?: false
        val isFavorite = arguments?.getBoolean("isFavorite", false) ?: false

        // Загружаем вакансию, если передан корректный ID
        if (vacancyId != null) {
            val source = if (openedFromFavorites) {
                VacancyDetailsSource.FAVORITES
            } else {
                VacancyDetailsSource.SEARCH
            }
            viewModel.loadVacancy(
                id = vacancyId,
                source = source,
                initialIsFavorite = isFavorite
            )
        }

        return ComposeView(requireContext()).apply {
            setContent {
                val state by viewModel.state.collectAsState()

                VacancyDetailsScreen(
                    vacancy = state.vacancy,
                    isFavorite = state.isFavorite,
                    isLoading = state.isLoading,
                    error = state.error,
                    onBack = { navigateBack() },
                    onShare = { shareVacancy() },
                    onFavoriteClick = { viewModel.toggleFavorite() },
                    onPhoneClick = { phone -> dialPhone(phone) },
                    onEmailClick = { email -> sendEmail(email) }
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

    private fun dialPhone(phone: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        startActivity(dialIntent)
    }

    private fun sendEmail(email: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
        }
        startActivity(emailIntent)
    }

    companion object {
        fun newInstance(): VacancyDetailsFragment = VacancyDetailsFragment()
    }
}
