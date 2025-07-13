package ru.practicum.android.diploma.presentation.mappers

import ru.practicum.android.diploma.domain.models.vacancies.Vacancy
import ru.practicum.android.diploma.presentation.models.vacancies.VacancyUiModel

fun Vacancy.toUiModel(): VacancyUiModel {
    return VacancyUiModel(
        nameVacancy = nameVacancy,
        alternateUrl = alternateUrl,
        id = id,
        employerName = employerName,
        logo = logo,
        salary = salary,
        city = city
    )
}
