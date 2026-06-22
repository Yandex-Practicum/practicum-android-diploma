package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.ApiResult
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.models.Vacancy

class VacanciesInteractorImpl(private val repository: VacanciesRepository) : VacanciesInteractor {
    override fun searchVacancies(query: String, page: Int): Flow<ApiResult<VacanciesSearchResult>> {
        return repository.searchVacancies(query, page)
    }

    override fun getVacancyDetails(vacancyId: String): Flow<ApiResult<Vacancy>> {
        return repository.getVacancyDetails(vacancyId)
    }
}
