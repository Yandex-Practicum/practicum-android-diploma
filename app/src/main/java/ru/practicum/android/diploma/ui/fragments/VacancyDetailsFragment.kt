package ru.practicum.android.diploma.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.bundle.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailsBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.details.VacancyDetailsEvent
import ru.practicum.android.diploma.presentation.details.VacancyDetailsState
import ru.practicum.android.diploma.presentation.details.VacancyDetailsViewModel
import ru.practicum.android.diploma.util.HtmlUtils
import ru.practicum.android.diploma.util.IntentHelper

class VacancyDetailsFragment : Fragment() {
    private var _binding: FragmentVacancyDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VacancyDetailsViewModel by viewModel {
        parametersOf(arguments?.getString("VACANCY_ID_KEY") ?: "")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVacancyDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupServerErrorState()

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        observeViewModel()
        observeEvents()

        binding.shareButton.setOnClickListener {
            viewModel.shareUrl()
        }

        binding.email.setOnClickListener {
            viewModel.onEmailClick()
        }

        binding.phone.setOnClickListener {
            viewModel.onPhoneClick()
        }

        binding.addToFavouritesButton.setOnClickListener {
            viewModel.onFavoritesClick()
        }
    }

    // обработка одноразовых событий
    private fun observeEvents() {
        lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is VacancyDetailsEvent.OpenPhone ->
                        IntentHelper.callPhone(requireContext(), event.phone)

                    is VacancyDetailsEvent.OpenEmail ->
                        IntentHelper.sendEmail(requireContext(), event.email)

                    is VacancyDetailsEvent.Share ->
                        IntentHelper.shareText(requireContext(), event.text)
                }
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                render(state)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isFavoriteState.collect { isInFavorites ->
                renderFavoriteState(isInFavorites)
            }
        }
    }

    private fun renderFavoriteState(isInFavorites: Boolean) {
        binding.addToFavouritesButton.setImageResource(
            if (isInFavorites) R.drawable.ic_favorites_on_24 else R.drawable.ic_favorites_off_24
        )
    }

    private fun render(state: VacancyDetailsState) {
        when (state) {
            is VacancyDetailsState.Loading -> {
                // Здесь можно добавить показ лоадера
                binding.vacancyContent.isVisible = false
                binding.layoutServerError.root.isVisible = false
            }

            is VacancyDetailsState.Content -> {
                showContent(state.vacancy)
            }

            is VacancyDetailsState.Error -> {
                showServerError()
            }
        }
    }

    private fun showContent(vacancy: Vacancy) {
        binding.layoutServerError.root.isVisible = false
        binding.vacancyContent.isVisible = true

        with(binding) {
            tvVacancyName.text = vacancy.vacancyName
            tvCompanyName.text = vacancy.companyName
            tvArea.text = vacancy.areaName
            tvExperience.text = vacancy.experienceName

            // Обработка зарплаты
            tvSalary.text = formatSalary(vacancy)

            // Загрузка логотипа
            Glide.with(requireContext())
                .load(vacancy.logoUrl)
                .placeholder(R.drawable.ic_droid)
                .error(R.drawable.ic_droid)
                .transform(CenterInside())
                .into(ivEmployerLogoValue)

            // Самое важное: парсинг HTML описания
            tvDescription.text = HtmlUtils.parseHtml(vacancy.description)

            // Вставка контактых данных
            showContacts(vacancy)
        }
    }

    private fun showContacts(vacancy: Vacancy) = with(binding) {
        val hasEmail = !vacancy.contactEmail.isNullOrBlank()
        val hasPhone = !vacancy.phoneFormatted.isNullOrBlank()
        val hasPhoneComment = !vacancy.phoneComment.isNullOrBlank()

        contactsTitle.isVisible = hasEmail || hasPhone

        email.isVisible = hasEmail
        if (hasEmail) {
            email.text = vacancy.contactEmail
        }

        phone.isVisible = hasPhone
        if (hasPhone) {
            phone.text = vacancy.phoneFormatted
        }

        phoneComment.isVisible = hasPhoneComment
        if (hasPhoneComment) {
            phoneComment.text = vacancy.phoneComment
        }
    }

    private fun formatSalary(vacancy: Vacancy): String {
        return when {
            vacancy.salaryFrom != null && vacancy.salaryTo != null -> {
                "От ${vacancy.salaryFrom} до ${vacancy.salaryTo} ${vacancy.currency}"
            }

            vacancy.salaryFrom != null -> {
                "От ${vacancy.salaryFrom} ${vacancy.currency}"
            }

            vacancy.salaryTo != null -> {
                "До ${vacancy.salaryTo} ${vacancy.currency}"
            }

            else -> "Зарплата не указана"
        }
    }

    private fun setupServerErrorState() {
        binding.layoutServerError.ivPlaceholderPicture.setImageResource(R.drawable.placeholder_server_error_vacancy)
        binding.layoutServerError.tvPlaceholderText.text = getString(R.string.server_error)
        binding.layoutServerError.tvPlaceholderText.isVisible = true
    }

    private fun showServerError() {
        binding.layoutServerError.root.isVisible = true
        binding.vacancyContent.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val VACANCY_ID_KEY = "VACANCY_ID_KEY"

        fun createArgs(vacancyId: String): Bundle =
            bundleOf(VACANCY_ID_KEY to vacancyId)
    }
}
