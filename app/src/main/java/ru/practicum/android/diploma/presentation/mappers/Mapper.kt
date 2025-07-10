package ru.practicum.android.diploma.presentation.mappers

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.salary.Salary
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy
import ru.practicum.android.diploma.presentation.models.vacancies.VacancyUiModel

fun Vacancy.toUiModel(context: Context): VacancyUiModel {
    return VacancyUiModel(
        nameVacancy = nameVacancy,
        alternateUrl = alternateUrl,
        id = id,
        employerName = employerName,
        logo = logo,
        salary = salary.format(context),
        city = city
    )
}

fun Salary.format(context: Context): String {
    return when(this) {
        is Salary.NotSpecifies -> context.getString(R.string.salary_not_specified)
        is Salary.Fixed -> context.getString(R.string.salary_fixed)
        is Salary.From -> context.getString(R.string.salary_from)
        else -> context.getString(R.string.salary_range)
    }
}
