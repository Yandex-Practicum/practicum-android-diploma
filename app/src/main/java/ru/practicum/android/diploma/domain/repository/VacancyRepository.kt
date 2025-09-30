package ru.practicum.android.diploma.domain.repository

import ru.practicum.android.diploma.domain.model.Vacancy

interface VacancyRepository {
    suspend fun getVacancies(): List<Vacancy>
}
