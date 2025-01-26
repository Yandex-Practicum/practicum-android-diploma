package ru.practicum.android.diploma.domain.search.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacanciesInteractor {
    fun searchVacancies(): Flow<Pair<List<Vacancy>?, Int?>>
}
