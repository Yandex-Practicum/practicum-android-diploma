package ru.practicum.android.diploma.vacancy.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.presentation.VacancyScreenState

interface VacancyDetailsRepository {
    fun getVacancyDetails(vacancyId: String): Flow<VacancyScreenState>
}
