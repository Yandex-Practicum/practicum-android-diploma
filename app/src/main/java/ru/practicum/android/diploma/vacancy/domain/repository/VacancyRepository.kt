package ru.practicum.android.diploma.vacancy.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

interface VacancyRepository {
    fun getVacancyDetails(id: String): Flow<Resource<VacancyDetails>>
}
