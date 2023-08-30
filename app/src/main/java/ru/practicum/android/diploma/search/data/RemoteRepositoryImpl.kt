package ru.practicum.android.diploma.search.data

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.VacancyRequest
import ru.practicum.android.diploma.search.domain.api.RemoteRepository
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.getMockVacancy
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val networkClient: NetworkClient):
    RemoteRepository {
    override suspend fun search(query: String): Flow<FetchResult> {
        val request = VacancyRequest.SearchVacanciesRequest(query)
        return networkClient.doRequest(request)
    }
    
    private fun getMockJobList(): List<Vacancy> {
        return List(20) { getMockVacancy() }
    }
}