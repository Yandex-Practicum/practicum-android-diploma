package ru.practicum.android.diploma.vacancy.details.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.VacancyDetail

interface VacancyRepository {
    fun getVacancyById(id: String): Flow<Result<VacancyDetail>>
}
