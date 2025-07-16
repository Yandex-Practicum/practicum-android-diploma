package ru.practicum.android.diploma.data.db.converter

import ru.practicum.android.diploma.data.db.entyties.FavouriteVacancy
import ru.practicum.android.diploma.domain.models.salary.Salary
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy

fun FavouriteVacancy.toDomain(): Vacancy {
    return Vacancy(
        nameVacancy = nameVacancy,
        alternateUrl = alternateUrl,
        id = id,
        employerName = employerName,
        logo = logo,
        salary = salary,
        city = city,
        timestamp = timestamp
    )
}

fun Vacancy.toData(): FavouriteVacancy {
    return FavouriteVacancy(
        id = id,
        nameVacancy = nameVacancy,
        alternateUrl = alternateUrl,
        employerName = employerName,
        logo = logo,
        salary = salary,
        city = city,
        timestamp = timestamp ?: System.currentTimeMillis()
    )
}
