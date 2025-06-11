package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.VacanciesRepository
import ru.practicum.android.diploma.domain.models.FilterOptions
import ru.practicum.android.diploma.domain.vacancy.VacanciesInteractor
import ru.practicum.android.diploma.domain.vacancy.models.VacanciesWithPage
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetail
import ru.practicum.android.diploma.util.Resource

class VacanciesInteractorImpl(
    private val repository: VacanciesRepository
) : VacanciesInteractor {
    override fun searchVacancies(filter: FilterOptions): Flow<Pair<VacanciesWithPage?, String?>> {
        return repository.searchVacancies(filter).map { result ->
            when (result) {
                is Resource.Success -> Pair(convertToPaginator(result.page, result.pages, result.data), null)
                is Resource.Error -> Pair(null, result.errorMessage)
            }
        }
    }

    override fun getVacancy(vacancyId: String): Flow<Pair<VacancyDetail?, String?>> {
        return repository.getVacancy(vacancyId).map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.errorMessage)
            }
        }
    }

    private fun convertToPaginator(page: Int, pages: Int, vacancies: List<Vacancy>?): VacanciesWithPage? {
        if (vacancies != null) {
            return VacanciesWithPage(page, pages, vacancies)
        }
        return null
    }
}
