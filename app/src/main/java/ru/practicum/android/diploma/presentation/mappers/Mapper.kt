package ru.practicum.android.diploma.presentation.mappers

import ru.practicum.android.diploma.data.db.entyties.FavouriteVacancy
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy
import ru.practicum.android.diploma.domain.models.vacancydetails.VacancyDetails
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

fun FavouriteVacancy.toFavouriteUi(): VacancyDetails {
    return VacancyDetails(
        id = this.id,
        name = this.name,
        salary = this.salary,
        employer = this.employer,
        experience = this.experience,
        employmentForm = this.employmentForm,
        description = this.description,
        workFormat = this.workFormat,
        alternateUrl = this.alternateUrl,
        keySkills = this.keySkills,
        city = this.city,
        logoUrl = this.logoUrl
    )
}

