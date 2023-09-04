package ru.practicum.android.diploma.details.data.network

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfoModel
import ru.practicum.android.diploma.filter.data.model.NetworkResponse
interface RemoteDataSource {
    suspend fun getVacancyFullInfo(id: String): Flow<NetworkResponse<VacancyFullInfoModel>>
}