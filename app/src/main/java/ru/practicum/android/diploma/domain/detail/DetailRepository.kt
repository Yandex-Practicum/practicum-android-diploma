package ru.practicum.android.diploma.domain.detail

import ru.practicum.android.diploma.domain.models.detail.FullVacancy

interface DetailRepository {
    fun getVavancy(): FullVacancy
}