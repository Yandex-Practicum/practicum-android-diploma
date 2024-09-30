package ru.practicum.android.diploma.search.data.repositoryimpl.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.data.networkclient.api.NetworkClient
import ru.practicum.android.diploma.data.networkclient.api.dto.HHApiIndustriesRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.HHApiRegionsRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.HHApiVacanciesRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.HHApiVacancyRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.HHIndustriesResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.HHRegionsResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.HHVacanciesResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.HHVacancyDetailResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.HttpStatus
import ru.practicum.android.diploma.search.domain.models.IndustryList
import ru.practicum.android.diploma.search.domain.models.RegionList
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancyDetail
import ru.practicum.android.diploma.search.domain.repository.VacanciesRepository
import ru.practicum.android.diploma.search.util.AreaConverter
import ru.practicum.android.diploma.search.util.IndustryConverter
import ru.practicum.android.diploma.search.util.VacancyConverter

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyConverter: VacancyConverter,
    private val industryConverter: IndustryConverter,
    private val areaConverter: AreaConverter
) : VacanciesRepository {
    override fun searchVacancies(options: Map<String, String>): Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.doRequest(HHApiVacanciesRequest(options))
        when (response.resultCode) {
            HttpStatus.NO_INTERNET -> {
                emit(Resource.Error("Check network connection"))
            }

            HttpStatus.OK -> {
                with(response as HHVacanciesResponse) {
                    emit(Resource.Success(vacancyConverter.mapItem(response.items)))
                }
            }

            HttpStatus.CLIENT_ERROR -> {
                emit(Resource.Error("Request was not accepted ${response.resultCode}"))
            }

            HttpStatus.SERVER_ERROR -> {
                emit(Resource.Error("Unexpcted error ${response.resultCode}"))
            }
        }
    }

    override fun listVacancy(id: String): Flow<Resource<VacancyDetail>> = flow {
        val response = networkClient.doRequest(HHApiVacancyRequest(id))
        when (response.resultCode) {
            HttpStatus.NO_INTERNET -> {
                emit(Resource.Error("Check network connection"))
            }

            HttpStatus.OK -> {
                with(response as HHVacancyDetailResponse) {
                    emit(Resource.Success(vacancyConverter.map(response)))
                }
            }

            HttpStatus.CLIENT_ERROR -> {
                emit(Resource.Error("Request was not accepted ${response.resultCode}"))
            }

            HttpStatus.SERVER_ERROR -> {
                emit(Resource.Error("Unexpcted error ${response.resultCode}"))
            }
        }
    }

    override fun listAreas(): Flow<Resource<RegionList>> = flow {
        val response = networkClient.doRequest(HHApiRegionsRequest(""))
        when (response.resultCode) {
            HttpStatus.NO_INTERNET -> {
                emit(Resource.Error("Check network connection"))
            }

            HttpStatus.OK -> {
                with(response as HHRegionsResponse) {
                    emit(Resource.Success(areaConverter.map(response)))
                }
            }

            HttpStatus.CLIENT_ERROR -> {
                emit(Resource.Error("Request was not accepted ${response.resultCode}"))
            }

            HttpStatus.SERVER_ERROR -> {
                emit(Resource.Error("Unexpcted error ${response.resultCode}"))
            }
        }
    }

    override fun listIndustries(): Flow<Resource<List<IndustryList>>> = flow {
        val response = networkClient.doRequest(HHApiIndustriesRequest(""))
        when (response.resultCode) {
            HttpStatus.NO_INTERNET -> {
                emit(Resource.Error("Check network connection"))
            }

            HttpStatus.OK -> {
                with(response as HHIndustriesResponse) {
                    emit(Resource.Success(industryConverter.map(response)))
                }
            }

            HttpStatus.CLIENT_ERROR -> {
                emit(Resource.Error("Request was not accepted ${response.resultCode}"))
            }

            HttpStatus.SERVER_ERROR -> {
                emit(Resource.Error("Unexpcted error ${response.resultCode}"))
            }
        }
    }

    companion object {
        private const val TAG = "VacanciesRepositoryImpl"
    }
}
