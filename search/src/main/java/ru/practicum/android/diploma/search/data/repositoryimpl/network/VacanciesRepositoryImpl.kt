package ru.practicum.android.diploma.search.data.repositoryimpl.network

import android.content.Context
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
import ru.practicum.android.diploma.data.networkclient.api.dto.Response
import ru.practicum.android.diploma.search.domain.models.IndustryList
import ru.practicum.android.diploma.search.domain.models.PaginationInfo
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
    private val areaConverter: AreaConverter,
    private val context: Context,
) : VacanciesRepository {
    override fun searchVacancies(options: Map<String, String>): Flow<Resource<PaginationInfo>> =
        executeRequest<Response, PaginationInfo>(
            request = { networkClient.doRequest(HHApiVacanciesRequest(options)) },
            successHandler = { response: Response ->
                Resource.Success(
                    PaginationInfo(
                        vacancyConverter.mapItem((response as HHVacanciesResponse).items),
                        response.page,
                        response.pages,
                        response.found
                    )
                )
            }
        )

    override fun listVacancy(id: String): Flow<Resource<VacancyDetail>> = executeRequest<Response, VacancyDetail>(
        request = { networkClient.doRequest(HHApiVacancyRequest(id)) },
        successHandler = { response: Response ->
            Resource.Success(vacancyConverter.map(response as HHVacancyDetailResponse))
        }
    )

    override fun listAreas(): Flow<Resource<RegionList>> = executeRequest<Response, RegionList>(
        request = { networkClient.doRequest(HHApiRegionsRequest(hashMapOf(Pair("id", DEFAULT_REGION)))) },
        successHandler = { response: Response ->
            Resource.Success(areaConverter.map(response as HHRegionsResponse))
        },
    )

    override fun listIndustries(): Flow<Resource<List<IndustryList>>> =
        executeRequest<Response, List<IndustryList>>(
            request = { networkClient.doRequest(HHApiIndustriesRequest(emptyMap())) },
            successHandler = { response: Response ->
                Resource.Success(industryConverter.map(response as HHIndustriesResponse))
            }
        )

    private fun <T, R> executeRequest(request: suspend () -> T, successHandler: (T) -> Resource<R>): Flow<Resource<R>> =
        flow {
            val response: T = request.invoke()
            when ((response as Response).resultCode) {
                HttpStatus.NO_INTERNET -> {
                    emit(
                        Resource.Error(
                            context.getString(ru.practicum.android.diploma.search.R.string.check_network_connection)
                        )
                    )
                }

                HttpStatus.OK -> {
                    with(response as T) {
                        emit(successHandler(response))
                    }
                }

                HttpStatus.CLIENT_ERROR -> {
                    emit(
                        Resource.Error(
                            context.getString(
                                ru.practicum.android.diploma.search.R.string.request_was_not_accepted,
                                response.resultCode,
                            )
                        )
                    )
                }

                HttpStatus.SERVER_ERROR -> {
                    emit(
                        Resource.Error(
                            context.getString(
                                ru.practicum.android.diploma.search.R.string.unexpcted_error,
                                response.resultCode
                            )
                        )
                    )
                }
            }
        }

    companion object {
        private const val TAG = "VacanciesRepositoryImpl"
        private const val DEFAULT_REGION = "113" // Russia
    }
}
