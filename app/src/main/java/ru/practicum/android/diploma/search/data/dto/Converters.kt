package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.search.data.dto.components.Salary
import ru.practicum.android.diploma.search.domain.models.Vacancy

fun VacancyDto.toVacancy(): Vacancy {
    return Vacancy(
        id = this.id,
        name = this.name,
        company = this.employer.name,
        salary = this.salary?.toStr() ?: "salary: null",
        area = this.area.name,
        icon = this.employer.logoUrls?.logo240 ?: ""
    )
}

fun Salary.toStr(): String {
    return "от ${this.from} до ${this.to} валюта: ${this.currency} gross: ${this.gross}"
}
