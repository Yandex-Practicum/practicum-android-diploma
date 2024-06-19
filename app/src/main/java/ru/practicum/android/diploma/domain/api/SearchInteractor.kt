package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface SearchInteractor {
    var currentPage: Int?
    var foundItems: Int?
    var pages: Int?
    fun searchVacancies(text: String, page: Int): Flow<Pair<List<Vacancy>?, String?>>
    suspend fun getDetails(id: String): Resource<Vacancy>
}
