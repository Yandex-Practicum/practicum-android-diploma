package ru.practicum.android.diploma.presentation.vacancy

import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.ui.common.UiModel
import ru.practicum.android.diploma.util.formatWithThousandsSeparator

sealed class VacancyDetailsItemUiModel : UiModel<VacancyDetailsItemUiModel> {
    data class VacancyName(
        private val vacancyDetails: VacancyDetails
    ) : VacancyDetailsItemUiModel() {
        val name = vacancyDetails.vacancyName
        val from = vacancyDetails.salaryFrom?.formatWithThousandsSeparator()
        val to = vacancyDetails.salaryTo?.formatWithThousandsSeparator()
        val currency = vacancyDetails.currency

        override fun areItemsTheSame(other: VacancyDetailsItemUiModel) = other is VacancyName
        override fun areContentsTheSame(other: VacancyDetailsItemUiModel) = other is VacancyName
    }

    data class VacancyCompany(
        private val vacancyDetails: VacancyDetails
    ) : VacancyDetailsItemUiModel() {
        val logoUrl = vacancyDetails.employerLogo
        val name = vacancyDetails.employerName
        val region = vacancyDetails.address

        override fun areItemsTheSame(other: VacancyDetailsItemUiModel) = other is VacancyCompany
        override fun areContentsTheSame(other: VacancyDetailsItemUiModel) = other is VacancyCompany
    }

    data class VacancyExperience(
        private val vacancyDetails: VacancyDetails
    ) : VacancyDetailsItemUiModel() {
        val experience = vacancyDetails.experience
        val schedule = buildString {
            append(vacancyDetails.employmentForm)
            if (!vacancyDetails.workFormat.isNullOrEmpty()) {
                append(", ")
                append(vacancyDetails.workFormat.joinToString(", "))
            }
        }

        override fun areItemsTheSame(other: VacancyDetailsItemUiModel) = other is VacancyExperience
        override fun areContentsTheSame(other: VacancyDetailsItemUiModel) =
            other is VacancyExperience
    }

    data class VacancyDescription(
        private val vacancyDetails: VacancyDetails
    ) : VacancyDetailsItemUiModel() {
        val description = vacancyDetails.description

        override fun areItemsTheSame(other: VacancyDetailsItemUiModel) = other is VacancyDescription
        override fun areContentsTheSame(other: VacancyDetailsItemUiModel) =
            other is VacancyDescription
    }

    data class VacancyKeySkills(
        private val vacancyDetails: VacancyDetails
    ) : VacancyDetailsItemUiModel() {
        val keySkills by lazy { vacancyDetails.keySkills }

        override fun areItemsTheSame(other: VacancyDetailsItemUiModel) = other is VacancyKeySkills
        override fun areContentsTheSame(other: VacancyDetailsItemUiModel) =
            other is VacancyKeySkills
    }
}
