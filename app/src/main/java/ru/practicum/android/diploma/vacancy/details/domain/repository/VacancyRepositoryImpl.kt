package ru.practicum.android.diploma.vacancy.details.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.domain.model.VacancyDetail

class VacancyRepositoryImpl : VacancyRepository {
    override fun getVacancyById(id: String): Flow<Result<VacancyDetail>> = flow {
    }
}
