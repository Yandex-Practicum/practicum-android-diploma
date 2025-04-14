package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.domain.api.IVacancyRepository

class IVacancyRepositoryImpl(private val networkClient: IApiService): IVacancyRepository {

    override suspend fun searchVacancies(
        expression: String): Flow<Resource<List<Vacancy>>> = flow {
           emit(networkClient.searchVacancies(searchVacanciesRequest.text))
    }

    override suspend fun getCountries(expression: String): Flow<Resource<List<Area>>> = flow{
       emit(networkClient.getAreas(getAreasRequest.locale))
    }

    override suspend fun getRegion(expression: String): Flow<Resource<List<Area>>> = flow {
        emit(networkClient.getAreas(getAreasRequest.locale))
    }

    override suspend fun getVacancyDetails(expression: String): Flow<Resource<List<VacancyDetails>>> = flow{
        emit(networkClient.getVacancyDetails(getVacancyDetailsRequest.vacancyId)
    }

    override suspend fun getIndustries(expression: String): Flow<Resource<List<Industry>>>  = flow {
        emit(networkClient.getIndustries(getIndustriesRequest.locale))
    }

}
