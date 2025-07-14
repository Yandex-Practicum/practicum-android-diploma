package ru.practicum.android.diploma.data.vacancysearchscreen.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.models.vacancies.VacanciesRequest
import ru.practicum.android.diploma.data.models.vacancies.VacanciesResponseDto
import ru.practicum.android.diploma.data.models.vacancydetails.VacancyDetailsRequest
import ru.practicum.android.diploma.data.models.vacancydetails.VacancyDetailsResponseDto
import ru.practicum.android.diploma.data.vacancysearchscreen.network.NetworkClient
import ru.practicum.android.diploma.domain.models.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy
import ru.practicum.android.diploma.domain.models.vacancydetails.VacancyDetails
import ru.practicum.android.diploma.util.Resource

class VacanciesRepositoryImpl(private val networkClient: NetworkClient) : VacanciesRepository {
    override fun search(text: String): Flow<List<Vacancy>?> = flow {
        try {
            val response = networkClient.doRequest(VacanciesRequest(text))
            val code = response.resultCode
            if (code == SEARCH_SUCCESS) {
                val res = (response as VacanciesResponseDto).items
                if (res.isNotEmpty()) {
                    val data = res.map { it.toDomain() }
                    emit(data)
                } else {
                    emit(emptyList())
                }
            } else {
                emit(null)
            }
        } catch (e: retrofit2.HttpException) {
            Log.e("Repository", "Search error", e)
            throw e
        }
    }

    override fun getVacancyDetailsById(id: String): Flow<Resource<VacancyDetails>> = flow {
        val response = networkClient.doRequest(VacancyDetailsRequest(id))
        when (response.resultCode) {
            SEARCH_SUCCESS -> {
                val data = (response as VacancyDetailsResponseDto).toDomain()
                emit(Resource.Success(data))
            }

            else -> emit(Resource.Error(message = "$ERROR: ${response.resultCode}"))
        }
    }

    companion object {
        private const val SEARCH_SUCCESS = 2
        private const val ERROR = "Error"
    }
}
