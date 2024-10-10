package ru.practicum.android.diploma.filter.data.repositoryimpl.network

import android.content.Context
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.commonutils.network.Response
import ru.practicum.android.diploma.commonutils.network.executeNetworkRequest
import ru.practicum.android.diploma.data.networkclient.api.NetworkClient
import ru.practicum.android.diploma.data.networkclient.api.dto.request.HHApiRegionsRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.response.regions.HHRegionsResponse
import ru.practicum.android.diploma.filter.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.domain.repository.RegionRepository
import ru.practicum.android.diploma.filter.util.AreaConverter

internal class RegionsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val areaConverter: AreaConverter,
    private val context: Context,
) : RegionRepository {
    override fun listAreas(): Flow<Resource<List<AreaInReference>>> =
        context.executeNetworkRequest<Response, List<AreaInReference>>(
            request = { networkClient.doRequest(HHApiRegionsRequest()) },
            successHandler = { response: Response ->
                Resource.Success(areaConverter.map(response as HHRegionsResponse))
            },
        )
}
