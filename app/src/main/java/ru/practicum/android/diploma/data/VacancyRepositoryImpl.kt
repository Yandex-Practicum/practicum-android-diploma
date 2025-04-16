package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.mapper.MapperSearchVacancyRequestResponse
import ru.practicum.android.diploma.data.mapper.MapperVacancyDetails
import ru.practicum.android.diploma.data.network.dto.GetAreasRequest
import ru.practicum.android.diploma.data.network.dto.GetIndustriesRequest
import ru.practicum.android.diploma.data.network.dto.GetVacancyDetailsRequest
import ru.practicum.android.diploma.domain.api.IVacancyRepository
import ru.practicum.android.diploma.domain.api.Resource
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.ReceivedVacanciesData
import ru.practicum.android.diploma.domain.models.SubIndustry
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyRepositoryImpl(
    private val networkClient: IRetrofitApiClient,
    private val filterParam: StorageRepositoryImpl,
    private val searchMapper: MapperSearchVacancyRequestResponse,
    private val vacancyDetailsMapper: MapperVacancyDetails
) : IVacancyRepository {

    override fun searchVacancies(expression: String): Flow<Resource<ReceivedVacanciesData>> = flow {
        val req = searchMapper.mapRequest(expression, filterParam.read())
        val result = networkClient.searchVacancies(req)
        val body = result.body()
        if (result.isSuccessful && body != null) {
            val vacanciesData = searchMapper.mapResponse(body)
            emit(Resource.Success(vacanciesData))
        } else {
            emit(Resource.Error(result.message(), null))
        }
    }

    override fun getCountries(): Flow<Resource<List<Area>>> = flow {
        val result = networkClient.getAreas(GetAreasRequest())
        val body = result.body()
        if (result.isSuccessful && body != null) {
            emit(Resource.Success(body.toList().map { Area(id = it.id, name = it.name, parentId = it.parentId) }))
        } else {
            emit(Resource.Error(result.message(), null))
        }
    }

    override fun getVacancyDetails(
        vacancyId: String
    ): Flow<Resource<VacancyDetails>> = flow {
        val result = networkClient.getVacancyDetails(GetVacancyDetailsRequest(vacancyId))
        val body = result.body()
        if (result.isSuccessful && body != null) {
            emit(Resource.Success(vacancyDetailsMapper.map(body.vacancyDetails)))
        } else {
            emit(Resource.Error(result.message(), null))
        }
    }

    override fun getIndustries(): Flow<Resource<List<Industry>>> = flow {
        val result = networkClient.getIndustries(GetIndustriesRequest())
        val body = result.body()
        if (result.isSuccessful && body != null) {
            emit(
                Resource.Success(
                    body.toList().map { it1 ->
                        Industry(
                            id = it1.id,
                            name = it1.name,
                            subIndustries = it1.subIndustries.map { SubIndustry(id = it.id, name = it.name) }
                        )
                    }
                )
            )
        } else {
            emit(Resource.Error(result.message(), null))
        }
    }
}
