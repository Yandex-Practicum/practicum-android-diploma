package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.model.DetailVacancy
import ru.practicum.android.diploma.domain.model.ErrorNetwork

interface DetailVacancyInteractor {
    suspend fun getDetailVacancy(id: String): Flow<Pair<DetailVacancy,
        ErrorNetwork>>
}
