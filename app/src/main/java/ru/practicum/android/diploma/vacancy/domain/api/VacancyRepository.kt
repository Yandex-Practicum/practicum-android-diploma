package ru.practicum.android.diploma.vacancy.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

interface VacancyRepository {
    fun getVacancy(id: Int): Flow<VacancyFull>
}
