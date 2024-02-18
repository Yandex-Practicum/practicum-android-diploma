package ru.practicum.android.diploma.commons.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.Resource
import ru.practicum.android.diploma.commons.domain.model.DetailVacancy

interface VacancyInteractor {
    suspend fun getDetailVacancy(id: String): Flow<Resource.Success<DetailVacancy>>

}
