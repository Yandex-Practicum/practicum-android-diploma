package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.SearchVacanciesOutcome

class SearchInteractorImpl(
    private val vacanciesRepository: VacanciesRepository,
) : SearchInteractor {
    override suspend fun searchVacancies(
        query: String,
        page: Int,
        area: Int?,
        industry: Int?,
        salary: Int?,
        onlyWithSalary: Boolean
    ): SearchVacanciesOutcome {
        val normalizedQuery = query.trim().lowercase()
        if (normalizedQuery.isEmpty()) {
            return SearchVacanciesOutcome.Error
        }
        return vacanciesRepository.searchVacancies(
            searchText = normalizedQuery,
            page = page,
            area = area,
            industry = industry,
            salary = salary,
            onlyWithSalary = onlyWithSalary
        )
    }
}
