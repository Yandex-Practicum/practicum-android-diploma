package ru.practicum.android.diploma.data.vacancysearchscreen.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.models.vacancies.VacanciesRequest
import ru.practicum.android.diploma.data.models.vacancies.VacanciesResponseDto
import ru.practicum.android.diploma.data.vacancysearchscreen.network.SearchNetworkClient
import ru.practicum.android.diploma.domain.models.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy

class VacanciesRepositoryImpl(private val networkClient: SearchNetworkClient) : VacanciesRepository {
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

    companion object {
        private const val SEARCH_SUCCESS = 2
    }
}
