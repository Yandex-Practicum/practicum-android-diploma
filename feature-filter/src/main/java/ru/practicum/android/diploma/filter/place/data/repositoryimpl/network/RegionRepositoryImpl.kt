package ru.practicum.android.diploma.filter.place.data.repositoryimpl.network

import android.content.Context
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.commonutils.network.Response
import ru.practicum.android.diploma.commonutils.network.executeNetworkRequest
import ru.practicum.android.diploma.data.networkclient.api.NetworkClient
import ru.practicum.android.diploma.data.networkclient.api.dto.request.HHApiRegionsRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.response.regions.HHRegionsResponse
import ru.practicum.android.diploma.filter.place.data.mappers.AreaMapper
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.place.domain.repository.RegionRepository

internal class RegionRepositoryImpl(
    private val networkClient: NetworkClient,
    private val areaMapper: AreaMapper,
    private val context: Context,
) : RegionRepository {
    override fun listAreas(): Flow<Resource<List<AreaInReference>>> =
        context.executeNetworkRequest<Response, List<AreaInReference>>(
            request = { networkClient.doRequest(HHApiRegionsRequest(null)) },
            successHandler = { response: Response ->
                Resource.Success(areaMapper.map(response as HHRegionsResponse))
            },
        )
}
