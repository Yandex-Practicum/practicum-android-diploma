package ru.practicum.android.diploma.data.industries

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.ResponseCodes
import ru.practicum.android.diploma.data.request.IndustriesRequest
import ru.practicum.android.diploma.data.response.Industries
import ru.practicum.android.diploma.data.response.IndustriesResponse
import ru.practicum.android.diploma.domain.industries.IndustriesRepository
import ru.practicum.android.diploma.util.Resource

class IndustriesRepositoryImpl(
    val networkClient: NetworkClient
) : IndustriesRepository {
    override fun searchIndustries(): Flow<Resource<List<Industries>>> = flow {
        val response = networkClient.doRequest(IndustriesRequest)

        when (response.resultCode) {
            ResponseCodes.DEFAULT -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SUCCESS -> {
                try {
                    with(response as IndustriesResponse) {
                        val data = this.result.map {
                            Industries(
                                id = it.id,
                                name = it.name
                            )
                        }
                        Log.d("StateData", "Состояние = $data")
                        emit(Resource.Success(data))
                    }
                } catch (e: Throwable) {
                    emit(Resource.Error(response.resultCode.code))
                }
            }

            ResponseCodes.ERROR -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.NO_CONNECTION -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SERVER_ERROR -> emit(Resource.Error(response.resultCode.code))
        }
    }
}
