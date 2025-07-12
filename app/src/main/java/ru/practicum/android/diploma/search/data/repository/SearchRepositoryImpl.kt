package ru.practicum.android.diploma.search.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.data.model.VacancyRequest
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.model.Resource
import ru.practicum.android.diploma.search.domain.model.VacancyPreview

class SearchRepositoryImpl(private val networkClient: NetworkClient) : SearchRepository {

    override fun getVacancies(text: String, area: String?): Flow<Resource<List<VacancyPreview>>> = flow {
        val response = networkClient.getVacancies(
            VacancyRequest(
                page = 0,
                perPage = 20,
                text = text,
                area = area
            )
        )

        when (response.resultCode) {

            200 -> {

            }

            404 -> {

            }

            -1 -> {

            }

            else -> {
                Log.d("SearchRepositoryImpl", "Result code: ${response.resultCode}")
            }
        }
    }
}
