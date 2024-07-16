package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.VacanciesResponse
import ru.practicum.android.diploma.search.domain.utils.Options
import ru.practicum.android.diploma.search.domain.utils.ResponseData

class SearchInteractorImpl(private val searchRepository: SearchRepository) : SearchInteractor {
    override fun search(options: Options): Flow<ResponseData<VacanciesResponse>> {
        return searchRepository.search(options)
    }
}
