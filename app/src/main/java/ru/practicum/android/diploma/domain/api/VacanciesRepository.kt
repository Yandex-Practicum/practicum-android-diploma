package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.model.DetailVacancy

interface VacanciesRepository {
    fun getDetailVacancy(
        id: String
    ): Flow<Pair<DetailVacancy?, Int>>
}
