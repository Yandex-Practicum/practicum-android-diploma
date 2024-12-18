package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.search.models.SearchParams

interface SearchInteractor {

    fun getVacancies(paramsForSearch: SearchParams): Flow<List<Vacancy>>

}
