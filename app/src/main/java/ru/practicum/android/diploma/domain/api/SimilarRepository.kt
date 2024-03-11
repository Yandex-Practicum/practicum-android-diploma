package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.main.SearchingVacancies
import ru.practicum.android.diploma.util.Resource

interface SimilarRepository {

    fun searchSimilarVacancies(vacancyId: String, page: Int): Flow<Resource<SearchingVacancies>>

    suspend fun similarVacanciesPagination(vacancyId: String, page: Int): Resource<SearchingVacancies>
}