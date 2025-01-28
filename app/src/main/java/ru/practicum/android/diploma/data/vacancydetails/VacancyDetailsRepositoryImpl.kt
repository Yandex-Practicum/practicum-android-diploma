package ru.practicum.android.diploma.data.vacancydetails

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsRepository
import ru.practicum.android.diploma.util.ResponseCode

class VacancyDetailsRepositoryImpl(
    private val vacanciesConverter: VacanciesConverter,
    private val networkClient: NetworkClient,
) : VacancyDetailsRepository {

    override fun searchVacancyById(vacancyId: String): Flow<Resource<Vacancy>> = flow {
        val response = networkClient.doRequestVacancyDetails(vacancyId)

        when (response.resultCode) {
            ResponseCode.NO_INTERNET.code -> emit(Resource.Error(ResponseCode.NO_INTERNET.code))

            ResponseCode.SERVER_ERROR.code -> emit(Resource.Error(ResponseCode.SERVER_ERROR.code))

            ResponseCode.SUCCESS.code -> {
                with(response as VacancyDto) {
                    val data = vacanciesConverter.convertFull(response)
                    // Тут нужно будет проверять находится ли вакансия в избранном или нет
                    emit(Resource.Success(data))
                }
            }
        }
    }
}
