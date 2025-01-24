package ru.practicum.android.diploma.domain.vacancy.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacancyInteractor {
    fun execute(id: String): Flow<Vacancy?>
}
