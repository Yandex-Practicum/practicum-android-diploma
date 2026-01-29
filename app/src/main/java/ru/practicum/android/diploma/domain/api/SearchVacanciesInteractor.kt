package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface SearchVacanciesInteractor {
    fun searchVacancies(expression: String): Flow<List<Vacancy>>

}
