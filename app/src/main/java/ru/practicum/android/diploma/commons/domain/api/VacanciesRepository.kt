package ru.practicum.android.diploma.commons.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commons.domain.model.DetailVacancy

interface VacanciesRepository {
    fun getDetailVacancy(
        id: String
    ): Flow<Pair<DetailVacancy?, Int>>
}
