package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.Area
import ru.practicum.android.diploma.data.dto.Industry
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.data.network.dto.GetAreasRequest
import ru.practicum.android.diploma.data.network.dto.GetIndustriesRequest
import ru.practicum.android.diploma.data.network.dto.GetVacancyDetailsRequest
import ru.practicum.android.diploma.data.network.dto.GetVacancyDetailsResponse
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesRequest
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesResponse
import ru.practicum.android.diploma.domain.api.IVacancyRepository
import ru.practicum.android.diploma.domain.api.Resource

class VacancyRepositoryImpl(private val networkClient: IRetrofitApiClient) : IVacancyRepository {

    override fun searchVacancies(req: SearchVacanciesRequest): Flow<Resource<SearchVacanciesResponse>> = flow {
        val result = networkClient.searchVacancies(req)
        val body = result.body()
        if (result.isSuccessful && body != null) {
            emit(Resource.Success(body))
        } else {
            emit(Resource.Error(result.message(), null))
        }
    }

    override fun getCountries(): Flow<Resource<List<Area>>> = flow {
        val result = networkClient.getAreas(GetAreasRequest())
        val body = result.body()
        if (result.isSuccessful && body != null) {
            emit(Resource.Success(body.toList()))
        } else {
            emit(Resource.Error(result.message(), null))
        }
    }

    override fun getRegion(): Flow<Resource<List<Area>>> = getCountries()

    override fun getVacancyDetails(
        req: GetVacancyDetailsRequest
    ): Flow<Resource<VacancyDetails>> = flow {
        val result = networkClient.getVacancyDetails(req)
        val body = result.body()
        if (result.isSuccessful && body != null) {
            emit(Resource.Success(parseVacancyDetailsResponse(body)))
        } else {
            emit(Resource.Error(result.message(), null))
        }
    }

    override fun getIndustries(): Flow<Resource<List<Industry>>> = flow {
        val result = networkClient.getIndustries(GetIndustriesRequest())
        val body = result.body()
        if (result.isSuccessful && body != null) {
            emit(Resource.Success(body.toList()))
        } else {
            emit(Resource.Error(result.message(), null))
        }
    }

    private fun parseVacancyDetailsResponse(response: GetVacancyDetailsResponse) = VacancyDetails(
        id = response.id,
        name = response.name,
        area = response.area,
        description = response.description,
        employer = response.employer,
        keySkills = response.keySkills,
        salary = response.salary,
        salaryRange = response.salaryRange,
        experience = response.experience
    )

}
