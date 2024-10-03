package ru.practicum.android.diploma.vacancy.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.domain.model.Vacancy

interface VacancyDetailInteractor {
    fun listVacancy(id: String): Flow<Pair<Vacancy?, String?>>
}
