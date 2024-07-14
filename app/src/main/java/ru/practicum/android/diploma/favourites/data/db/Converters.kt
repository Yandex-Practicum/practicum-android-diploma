package ru.practicum.android.diploma.favourites.data.db

import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

fun VacancyEntity.toVacancy(): Vacancy {
    return Vacancy(
        id = vacancyId,
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
        id = vacancyId,
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
        keySkills = keySkills
    )
}

fun VacancyFull.toVacancyEntity(): VacancyEntity {
    return VacancyEntity(
        id = null,
        vacancyId = id,
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
        keySkills = keySkills
    )
}
