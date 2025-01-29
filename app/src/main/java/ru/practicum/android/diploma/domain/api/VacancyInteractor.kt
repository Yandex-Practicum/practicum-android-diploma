package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Page
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacancyInteractor {
    fun getVacancies(options: Map<String, String>): Flow<Resource<Page>>
    suspend fun getVacancy(vacancyId: String): Resource<Vacancy>
}
