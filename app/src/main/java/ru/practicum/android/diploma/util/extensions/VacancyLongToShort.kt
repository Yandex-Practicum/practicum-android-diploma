package ru.practicum.android.diploma.util.extensions

import ru.practicum.android.diploma.domain.models.main.VacancyLong
import ru.practicum.android.diploma.domain.models.main.VacancyShort

fun VacancyLong.toVacancyShort(): VacancyShort {
    return VacancyShort(
        postedAt = this.publishedAt,
        vacancyId = this.vacancyId,
        logoUrl = this.logoUrl,
        name = this.name,
        area = this.areaName,
        employer = this.employer?.name ?: "",
        salary = this.salary,
        schedule = this.schedule,
        industries = this.industries
    )
}
