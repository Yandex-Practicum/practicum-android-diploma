package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.VacanciesResponse
import ru.practicum.android.diploma.data.network.VacanciesRequest
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient,
) : VacanciesRepository {

    override suspend fun searchVacancies(searchText: String, page: Int): VacanciesSearchResult? {
        val response = networkClient.doRequest(
            VacanciesRequest(searchText = searchText, page = page),
        )
        if (response.resultCode != HTTP_OK) {
            return null
        }
        val data = response.data as? VacanciesResponse ?: return null
        return data.toDomain()
    }

    private companion object {
        const val HTTP_OK = 200
    }
}
