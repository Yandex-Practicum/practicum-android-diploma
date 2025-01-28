package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.VacancyResponse
import ru.practicum.android.diploma.domain.search.api.VacanciesRepository
import ru.practicum.android.diploma.util.ResponseCode

class VacanciesRepositoryImpl(
    private val vacanciesConverter: VacanciesConverter,
    private val networkClient: NetworkClient,
) : VacanciesRepository {
    override fun searchVacancies(text: String?, options: HashMap<String, Int>): Flow<Resource<VacancyResponse>> = flow {
        val response = networkClient.doRequestVacancies(text = text, options = options)
        when (response.resultCode) {
            ResponseCode.NO_INTERNET.code -> emit(Resource.Error(ResponseCode.NO_INTERNET.code))

            ResponseCode.SERVER_ERROR.code -> emit(Resource.Error(ResponseCode.SERVER_ERROR.code))

            ResponseCode.SUCCESS.code -> {
                with(response as VacanciesResponseDto) {
                    val data = vacanciesConverter.convertCut(response)
                    emit(Resource.Success(data))
                }
            }
        }
    }
}
