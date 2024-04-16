package ru.practicum.android.diploma.domain.impl.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.search.VacanciesSearchRepository
import ru.practicum.android.diploma.domain.api.search.VacancySearchInteractor
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.models.vacacy.VacancyResponse

class VacancySearchInteractorImpl(private val vacancySearchRepository: VacanciesSearchRepository) :
    VacancySearchInteractor {
    override fun getVacancies(query: String, page: Int, filters: Filters): Flow<Pair<VacancyResponse?, String?>> {
        return vacancySearchRepository.getVacancies(query, page, filters)
    }
}
