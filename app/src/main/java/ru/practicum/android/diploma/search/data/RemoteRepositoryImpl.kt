package ru.practicum.android.diploma.search.data

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.VacancyRequest
import ru.practicum.android.diploma.search.domain.api.RemoteRepository
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.getMockVacancy
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
    private val logger: Logger,
) : RemoteRepository {
    
    override suspend fun search(query: String): Flow<FetchResult> {
        logger.log(thisName, "search -> $query")
        val request = VacancyRequest.SearchVacanciesRequest(query)
        return networkClient.doRequest(request)
    }
    
    private fun getMockJobList(): List<Vacancy> {
        val list = List(20) { getMockVacancy() }
        logger.log(thisName, "getMockJobList -> $list")
        return list
    }
}