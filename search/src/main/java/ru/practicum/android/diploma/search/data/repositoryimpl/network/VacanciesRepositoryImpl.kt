package ru.practicum.android.diploma.search.data.repositoryimpl.network

import android.content.Context
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.commonutils.network.Response
import ru.practicum.android.diploma.commonutils.network.executeNetworkRequest
import ru.practicum.android.diploma.data.networkclient.api.NetworkClient
import ru.practicum.android.diploma.data.networkclient.api.dto.request.HHApiIndustriesRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.request.HHApiRegionsRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.request.HHApiVacanciesRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.request.HHApiVacancyRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.response.industries.HHIndustriesResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.response.regions.HHRegionsResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancies.HHVacanciesResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.HHVacancyDetailResponse
import ru.practicum.android.diploma.search.domain.models.IndustryList
import ru.practicum.android.diploma.search.domain.models.PaginationInfo
import ru.practicum.android.diploma.search.domain.models.RegionList
import ru.practicum.android.diploma.search.domain.models.VacancyDetail
import ru.practicum.android.diploma.search.domain.repository.VacanciesRepository
import ru.practicum.android.diploma.search.util.AreaConverter
import ru.practicum.android.diploma.search.util.IndustryConverter
import ru.practicum.android.diploma.search.util.VacancyConverter

private const val DEFAULT_REGION = "113" // Russia

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyConverter: VacancyConverter,
    private val industryConverter: IndustryConverter,
    private val areaConverter: AreaConverter,
    private val context: Context,
) : VacanciesRepository {
    override fun searchVacancies(options: Map<String, String>): Flow<Resource<PaginationInfo>> =
        context.executeNetworkRequest<Response, PaginationInfo>(
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

    override fun listVacancy(id: String): Flow<Resource<VacancyDetail>> =
        context.executeNetworkRequest<Response, VacancyDetail>(
            request = { networkClient.doRequest(HHApiVacancyRequest(id)) },
            successHandler = { response: Response ->
                Resource.Success(vacancyConverter.map(response as HHVacancyDetailResponse))
            }
        )

    override fun listAreas(): Flow<Resource<RegionList>> = context.executeNetworkRequest<Response, RegionList>(
        request = { networkClient.doRequest(HHApiRegionsRequest(hashMapOf(Pair("id", DEFAULT_REGION)))) },
        successHandler = { response: Response ->
            Resource.Success(areaConverter.map(response as HHRegionsResponse))
        },
    )

    override fun listIndustries(): Flow<Resource<List<IndustryList>>> =
        context.executeNetworkRequest<Response, List<IndustryList>>(
            request = { networkClient.doRequest(HHApiIndustriesRequest(emptyMap())) },
            successHandler = { response: Response ->
                Resource.Success(industryConverter.map(response as HHIndustriesResponse))
            }
        )
}
