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
        savedInstanceState: Bundle?
    ): View? {
        // Получаем аргументы из навигации
        val vacancyId = arguments?.getString("vacancyId")
        val openedFromFavorites = arguments?.getBoolean("openedFromFavorites", false) ?: false

        // Загружаем вакансию
        if (vacancyId != null) {
            val source = if (openedFromFavorites) {
                VacancyDetailsSource.FAVORITES
            } else {
                VacancyDetailsSource.SEARCH
            }
            viewModel.loadVacancy(vacancyId, source)
        } else {
            // Если ID не передан, загружаем вакансию для тестирования
//            viewModel.loadVacancy("0004f537-766a-45dc-beaa-934c605353a8", VacancyDetailsSource.SEARCH)
            viewModel.loadVacancy("0002af81-c469-46fe-ba9e-8e2f339086c7", VacancyDetailsSource.SEARCH)
//            viewModel.loadVacancy("123", VacancyDetailsSource.SEARCH)
        }

        return ComposeView(requireContext()).apply {
            setContent {
                val vacancy by viewModel.vacancy.collectAsState()
                val isFavorite by viewModel.isFavorite.collectAsState()
                val isLoading by viewModel.isLoading.collectAsState()
                val error by viewModel.error.collectAsState()

                VacancyDetailsScreen(
                    vacancy = vacancy,
                    isFavorite = isFavorite,
                    isLoading = isLoading,
                    error = error,
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
