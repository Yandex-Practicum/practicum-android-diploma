package ru.practicum.android.diploma.search.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.data.model.VacanciesResponse
import ru.practicum.android.diploma.search.data.model.VacancyRequest
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.model.FailureType
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
            OK_RESPONSE -> {
                val data = (response as VacanciesResponse).items.map {
                    VacancyPreview(
                        it.id,
                        it.name,
                        it.employer.name,
                        it.salary.from ?: 0,
                        it.salary.to ?: 0,
                        it.salary.currency ?: "",
                        it.employer.logoUrl ?: ""
                    )
                }
                emit(Resource.Success(data))
            }

            NOT_FOUND -> {
                emit(Resource.Failed(FailureType.NotFound))
            }

            NO_INTERNET -> {
                emit(Resource.Failed(FailureType.NoInternet))
            }

            else -> {
                Log.d("SearchRepositoryImpl", "Result code: ${response.resultCode}")
                emit(Resource.Failed(FailureType.ApiError))
            }
        }
    }

    companion object {
        const val OK_RESPONSE = 200
        const val NO_INTERNET = -1
        const val NOT_FOUND = 404
    }
}
