package ru.practicum.android.diploma.data.industries

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.ResponseCodes
import ru.practicum.android.diploma.data.converters.IndustriesConverter.mapToIndustriesAllDeal
import ru.practicum.android.diploma.data.converters.VacancyConverter.toVacancyDetail
import ru.practicum.android.diploma.data.request.IndustriesRequest
import ru.practicum.android.diploma.data.response.IndustriesResponse
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDtoResponse
import ru.practicum.android.diploma.domain.industries.IndustriesAllDeal
import ru.practicum.android.diploma.domain.industries.IndustriesRepository
import ru.practicum.android.diploma.util.Resource

class IndustriesRepositoryImpl(
    val networkClient: NetworkClient
) : IndustriesRepository {
    override fun searchIndustries(): Flow<Resource<List<IndustriesAllDeal>>> = flow {
        val response = networkClient.doRequest(IndustriesRequest)

        Log.d("ResultIndustries", "Вакансии в успешном результате == ${response.resultCode}")

        when (response.resultCode) {
            ResponseCodes.DEFAULT -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SUCCESS -> {
                try {
                    Log.d("ResultIndustries", "У нас не пустой результат в Repository")
                    val industriesResponse = response as IndustriesResponse
                    val industriesAllDealList = industriesResponse.mapToIndustriesAllDeal()
                    emit(Resource.Success((listOf(industriesAllDealList))))
                } catch (e: Throwable) {
                    Log.d("ResultIndustries", "Ловим Exception")
                    emit(Resource.Error(response.resultCode.code))
                }
            }

            ResponseCodes.ERROR -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.NO_CONNECTION -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SERVER_ERROR -> emit(Resource.Error(response.resultCode.code))
        }
    }
}
