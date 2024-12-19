package ru.practicum.android.diploma.data.vacancy.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.VacancyRequest
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto
import ru.practicum.android.diploma.data.dto.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.vacancy.VacancyRepository
import ru.practicum.android.diploma.domain.NetworkClient
import ru.practicum.android.diploma.util.Resource

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancyRepository {

    override fun getVacancyId(id: String): Flow<Resource<VacancyFullItemDto>> = flow {
        val response = networkClient.doRequest(VacancyRequest(id))
        emit(
            when (response.code) {
                RetrofitNetworkClient.INTERNET_NOT_CONNECT -> Resource.Error("Нет интернета")
                RetrofitNetworkClient.HTTP_OK_CODE -> {
                    with(response as VacancyResponse) {
                        Resource.Success(response.items)
                    }
                }
                else -> {
                    Resource.Error("Ошибка сервера")
                }
            }
        )
    }
}
