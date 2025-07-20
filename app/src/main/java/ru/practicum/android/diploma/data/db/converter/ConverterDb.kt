package ru.practicum.android.diploma.data.db.converter

import ru.practicum.android.diploma.data.db.entyties.FavouriteVacancy
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy
import ru.practicum.android.diploma.domain.models.vacancydetails.EmploymentForm

fun FavouriteVacancy.toDomain(): Vacancy {
    return Vacancy(
        nameVacancy = name,
        alternateUrl = alternateUrl,
        id = id,
        employerName = employmentForm?.name,
        logo = logoUrl,
        salary = salary,
        city = city,
        timestamp = timestamp
    )
}

fun Vacancy.toData(): FavouriteVacancy {
    return FavouriteVacancy(
        id = id,
        name = nameVacancy,
        alternateUrl = alternateUrl,
        employer = null,
        experience = null,
        employmentForm = employerName?.let { EmploymentForm(name = it, requiresSuffix = false) },
        description = "",
        workFormat = null,
        keySkills = null,
        logoUrl = logo,
        salary = salary,
        city = city,
        timestamp = timestamp ?: System.currentTimeMillis()
    )
}
