package ru.practicum.android.diploma.data.dto.mapper

import ru.practicum.android.diploma.domain.models.main.VacancyShort

data class VacancyPageResult(
    val vacancies: List<VacancyShort>,
    val found: Int
)
