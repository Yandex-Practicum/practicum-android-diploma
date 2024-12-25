package ru.practicum.android.diploma.domain.search.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.response.VacancySearchResponse
import ru.practicum.android.diploma.data.search.VacanciesRepository
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.models.SearchParams

class SearchInteractorImpl(
    private val vacanciesRepository: VacanciesRepository
) : SearchInteractor {

    override fun getVacancies(paramsForSearch: SearchParams): Flow<VacancySearchResponse> {
        return vacanciesRepository.getVacancies(paramsForSearch)
    }
}
