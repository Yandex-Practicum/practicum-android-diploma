package ru.practicum.android.diploma.domain.models.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy

interface VacanciesRepository {
    fun search(text: String): Flow<List<Vacancy>?>
}
