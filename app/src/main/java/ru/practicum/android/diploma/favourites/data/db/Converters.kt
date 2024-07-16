package ru.practicum.android.diploma.favourites.data.db

import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

fun VacancyEntity.toVacancy(): Vacancy {
    return Vacancy(
        id = id,
        name = name,
        company = company,
        currency = currency,
        salaryFrom = salaryFrom,
        salaryTo = salaryTo,
        area = area,
        icon = icon
    )
}

fun VacancyEntity.toVacancyFull(): VacancyFull {
    return VacancyFull(
        id = id,
        name = name,
        company = company,
        currency = currency,
        salaryFrom = salaryFrom,
        salaryTo = salaryTo,
        area = area,
        alternateUrl = alternateUrl,
        icon = icon,
        employment = employment,
        experience = experience,
        schedule = schedule,
        description = description,
        contact = contact,
        email = email,
        phone = phone,
        comment = comment,
        keySkills = keySkills,
        timestamp = timestamp
    )
}

fun VacancyFull.toVacancyEntity(timestamp: Long): VacancyEntity {
    return VacancyEntity(
        id = id,
        name = name,
        company = company,
        currency = currency,
        salaryFrom = salaryFrom,
        salaryTo = salaryTo,
        area = area,
        alternateUrl = alternateUrl,
        icon = icon,
        employment = employment,
        experience = experience,
        schedule = schedule,
        description = description,
        contact = contact,
        email = email,
        phone = phone,
        comment = comment,
        keySkills = keySkills,
        timestamp = timestamp
    )
}
