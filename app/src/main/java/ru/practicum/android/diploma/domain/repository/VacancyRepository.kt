package ru.practicum.android.diploma.domain.repository

import ru.practicum.android.diploma.domain.models.Vacancy

interface VacancyRepository {
    suspend fun getVacancies(): List<Vacancy>
}
