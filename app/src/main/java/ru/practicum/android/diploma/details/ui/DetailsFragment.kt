package ru.practicum.android.diploma.details.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailsBinding
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancyFullInfoModel
import ru.practicum.android.diploma.util.setImage
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding

/** Фрагмент для отображения детальной информации о вакансии */
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding<FragmentDetailsBinding>()
    private val viewModel: DetailsViewModel by viewModels { (activity as RootActivity).viewModelFactory }

    private var vacancy: Vacancy? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vacancy = requireArguments().getString(VACANCY_KEY)?.let { Json.decodeFromString<Vacancy>(it) }

        viewModel.log(thisName, "onViewCreated()")
        drawMainInfo()
        viewModel.getVacancyByID(vacancy?.id ?: 0)
        collector()
        pressSimilarVacanciesButton()
    }

    /** Функция для сбора данных из viewModel */
    private fun collector() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                viewModel.log(thisName, "uiState.collect { state -> ${state.thisName}")
                when (state) {
                    is DetailsScreenState.Empty -> Unit
                    is DetailsScreenState.Content -> {
                        showDescription(state.result)
                    }
                }
            }
        }
    }

    /** Функция для отображения(отрисовки) описания вакансии, опыта, графика работы, ключевых навыков, контактов */
    private fun showDescription(vacancy: VacancyFullInfoModel) {
        with(binding) {
            viewModel.log(thisName, "showDescription()")
            val tvSchedule = vacancy.employment?.name + ". " + vacancy.schedule?.name
            val formattedDescription =
                HtmlCompat.fromHtml(vacancy.description!!, HtmlCompat.FROM_HTML_MODE_LEGACY)
            val tvKeySkillsList =
                vacancy.keySkills?.mapIndexed { _, skill -> "• ${skill.name}" }?.joinToString("\n")
            val phoneList = vacancy.contacts?.phones?.mapIndexed { _, phone ->
                "+" + phone.country +
                        " (${phone.city}) " + phone.number
            }?.joinToString("\n")

            tvExperience.text = vacancy.experience?.name
            tvScheduleEmployment.text = tvSchedule
            tvDescription.text = formattedDescription
            tvKeySkills.text = tvKeySkillsList
            tvContactsName.text = vacancy.contacts?.name
            tvContactsEmail.text = vacancy.contacts?.email
            tvContactsPhone.text = phoneList
        }
    }

    /** Переход на фрагмент с похожими вакансиями */
    private fun pressSimilarVacanciesButton() {
        binding.buttonSameVacancy.setOnClickListener {
            viewModel.log(thisName, "buttonSameVacancy.setOnClickListener { }")
            findNavController().navigate(
                resId = R.id.action_detailsFragment_to_similarsVacancyFragment,
                args = bundleOf(VACANCY_KEY to Json.encodeToString(vacancy))
            )
        }
    }

    /** Функция для отображения(отрисовки) заголовка, зарплаты, формы. Информацию получаем из bundle */
    private fun drawMainInfo() {
        with(binding) {
            viewModel.log(thisName, "drawMainInfo()")
            vacancy?.let {
                tvNameOfVacancy.text = it.title
                tvSalary.text = it.salary
                tvNameOfCompany.text = it.company
                tvArea.text = it.area
                imageView.setImage(it.iconUri, R.drawable.ic_placeholder_company)
            }
        }
    }

    companion object {
        const val VACANCY_KEY = "KEY_DETAILS"
    }
}
