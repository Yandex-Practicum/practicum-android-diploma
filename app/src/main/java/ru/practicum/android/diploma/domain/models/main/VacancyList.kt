package ru.practicum.android.diploma.domain.models.main

import ru.practicum.android.diploma.data.dto.responseUnits.VacancyDto

data class VacancyList(
    val vacancyDto: Array<VacancyDto>,
    val found: Int,
    val page: Int,
    val pages: Int,)
