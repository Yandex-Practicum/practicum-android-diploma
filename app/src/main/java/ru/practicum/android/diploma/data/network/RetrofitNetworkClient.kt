package ru.practicum.android.diploma.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.data.dto.VacancyDto

class RetrofitNetworkClient(
    private val api: HhApi
) : NetworkClient {

    override suspend fun doRequestVacancies(): VacanciesResponseDto? {
        return withContext(Dispatchers.IO) {
            try {
                api.searchVacancies()
            } catch (e: HttpException) {
                Log.w("HttpException", e)
                null
            }
        }
    }

    override suspend fun doRequestVacancy(id: String): VacancyDto? {
        return withContext(Dispatchers.IO) {
            try {
                api.getVacancy(id)
            } catch (e: HttpException) {
                Log.w("HttpException", e)
                null
            }
        }
    }
}
