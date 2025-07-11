package ru.practicum.android.diploma.data.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.models.vacancies.VacanciesRequest
import ru.practicum.android.diploma.data.models.vacancies.VacanciesResponseDto
import ru.practicum.android.diploma.data.network.SearchNetworkClient
import ru.practicum.android.diploma.domain.models.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy
import java.io.IOException

class VacanciesRepositoryImpl(private val networkClient: SearchNetworkClient): VacanciesRepository {
    override fun search(text: String): Flow<List<Vacancy>> = flow{
        try {
            val response = networkClient.doRequest(VacanciesRequest(text))
            val code = response.resultCode
            when(code){
                200 ->{
                    val res = (response as VacanciesResponseDto).items
                    if(res.isNotEmpty()){
                        val data = res.map{it.toDomain()}
                        emit(data)
                    }
                    else{
                        emit(emptyList())
                    }
                }
                404 ->  throw IOException("API error: ${code}")
                500 -> throw IOException("API error: ${code}")
            }
        } catch (e: Exception) {
            Log.e("Repository", "Search error", e)
            throw e
        }
    }
}
