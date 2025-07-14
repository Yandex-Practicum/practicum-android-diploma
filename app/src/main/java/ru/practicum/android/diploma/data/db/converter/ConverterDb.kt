package ru.practicum.android.diploma.data.db.converter

import ru.practicum.android.diploma.data.db.entyties.FavouriteVacancy
import ru.practicum.android.diploma.domain.models.salary.Salary
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy

fun FavouriteVacancy.toDomain(): Vacancy {
    return Vacancy(
        nameVacancy = name,
        alternateUrl = "",
        id = id,
        employerName = null,
        logo = null,
        salary = Salary.NotSpecifies,
        city = null,
        timestamp = timestamp
    )
}

fun Vacancy.toData(): FavouriteVacancy {
    return FavouriteVacancy(
        id = id,
        name = nameVacancy,
        timestamp = timestamp ?: System.currentTimeMillis()
    )
}
