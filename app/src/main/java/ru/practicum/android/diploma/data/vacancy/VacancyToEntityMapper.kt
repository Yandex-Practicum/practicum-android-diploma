package ru.practicum.android.diploma.data.vacancy

import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy

fun Vacancy.toEntity(): VacancyEntity {
    return VacancyEntity(
        id = id,
        name = name,
        description = description,
        experience = experience,
        schedule = schedule,
        employment = employment,
        areaName = areaName,
        industryName = industryName,
        skills = skills?.joinToString(separator = "\n"),
        url = url,
        salaryFrom = salaryFrom,
        salaryTo = salaryTo,
        currency = currency,
        salaryTitle = salaryTitle,
        city = city,
        street = street,
        building = building,
        fullAddress = fullAddress,
        contactName = contactName,
        email = email,
        phones = phones?.joinToString(separator = "\n"),
        employerName = employerName,
        logoUrl = logoUrl ?: ""
    )
}
