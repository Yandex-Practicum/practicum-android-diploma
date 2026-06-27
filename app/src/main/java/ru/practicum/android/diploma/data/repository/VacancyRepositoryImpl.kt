package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.toModel
import ru.practicum.android.diploma.data.dto.VacanciesRequest
import ru.practicum.android.diploma.data.dto.VacanciesResponse
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailsResponse
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.ApiResult
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyRepositoryImpl(private var networkClient: NetworkClient) : VacanciesRepository {
    override fun searchVacancies(
        query: String,
        page: Int,
        settings: ru.practicum.android.diploma.domain.models.FilterSettings?
    ): Flow<ApiResult<VacanciesSearchResult>> = flow {
        emit(ApiResult.Loading)

        // Формируем запрос, передавая параметры фильтрации
        // Если параметры (ЗП, регион, отрасль) выбраны, они будут подставлены здесь
        val request = VacanciesRequest(
            text = query,
            page = page,
            salary = settings?.expectedSalary,
            onlyWithSalary = settings?.notShowWithoutSalary ?: false,
            area = settings?.regionId ?: settings?.countryId, // Если выбран город, берем его ID, иначе ID страны
            industry = settings?.industryId
        )

        val data = networkClient.searchVacancies(request)
        if (data.resultCode == SUCCESS_CODE && data is VacanciesResponse) {
            emit(ApiResult.Success(data.toModel()))
        } else {
            emit(ApiResult.Error(data.resultCode))
        }
    }

    override fun getVacancyDetails(vacancyId: String): Flow<ApiResult<Vacancy>> = flow {
        emit(ApiResult.Loading)

        val response = networkClient.getVacancyDetails(VacancyDetailsRequest(vacancyId))
        if (response.resultCode == SUCCESS_CODE && response is VacancyDetailsResponse) {
            emit(ApiResult.Success(response.vacancy.toModel()))
        } else {
            emit(ApiResult.Error(response.resultCode))
        }
    }

    companion object {
        private const val SUCCESS_CODE = 200
    }
}
