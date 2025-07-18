package ru.practicum.android.diploma.presentation.vacancy

import ru.practicum.android.diploma.domain.models.VacancyDetails

data class VacancyUiState(
    val isFetching: Boolean = false,
    val vacancyDetails: VacancyDetails? = null,
    val isError: Boolean = false,
    val isFavorite: Boolean = false
) {
    val items = buildList {
        if (vacancyDetails != null) {
            add(VacancyDetailsItemUiModel.VacancyName(vacancyDetails))
            add(VacancyDetailsItemUiModel.VacancyCompany(vacancyDetails))
            add(VacancyDetailsItemUiModel.VacancyExperience(vacancyDetails))
            add(VacancyDetailsItemUiModel.VacancyDescription(vacancyDetails))
            if (vacancyDetails.keySkills.isNotEmpty()) {
                add(VacancyDetailsItemUiModel.VacancyKeySkills(vacancyDetails))
            }
        }
    }

    val isContentVisible by lazy {
        !isFetching && !isError && items.isNotEmpty()
    }

    val isEmptyVisible by lazy {
        !isFetching && isError
    }
}
