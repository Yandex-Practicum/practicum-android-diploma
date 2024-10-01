package ru.practicum.android.diploma.search.data.impl

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.search.data.network.HeadHunterAPI
import ru.practicum.android.diploma.search.data.network.NetworkUtils
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancySearchParams

class SearchRepositoryImpl(
    private val api: HeadHunterAPI,
    private val context: Context
) : SearchRepository {

    override fun searchVacancies(params: VacancySearchParams): Flow<Resource<List<Vacancy>>> = flow {
        if (NetworkUtils.isNetworkAvailable(context)) {
            try {
                emit(Resource.Loading())

                val options = params.toMap()

                val response = api.searchVacancies(
                    authToken = null,  // Токен авторизации
                    userAgent = "userAgent",
                    options = options
                )

                if (response.vacancies.isNotEmpty()) {
                    emit(Resource.Success(
                        data = response.vacancies,
                        found = response.found,
                        page = response.page,
                        pages = response.pages
                    ))
                } else {
                    emit(Resource.Error("No vacancies found"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
            }
        } else {
            emit(Resource.Error("No Internet Connection"))
        }
    }.flowOn(Dispatchers.IO)
}
