package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacanciesInteractor {
    fun searchVacancies(page: Int):Flow<List<Vacancy>>
}
