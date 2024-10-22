package ru.practicum.android.diploma.filter.place.data.repositoryimpl.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.commonutils.network.Response
import ru.practicum.android.diploma.commonutils.network.executeNetworkRequest
import ru.practicum.android.diploma.data.networkclient.api.NetworkClient
import ru.practicum.android.diploma.data.networkclient.api.dto.request.HHApiRegionsRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.response.regions.HHRegionsResponse
import ru.practicum.android.diploma.filter.place.data.mappers.AreaMapper
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.place.domain.repository.NetworkRepository

private const val TAG = "RegionRepositoryImpl"
internal class NetworkRepositoryImpl(
    private val networkClient: NetworkClient,
    private val context: Context,
) : NetworkRepository {
    override fun listAreas(): Flow<Resource<List<AreaInReference>>> =
        context.executeNetworkRequest(
            request = { networkClient.doRequest(HHApiRegionsRequest) },
            successHandler = { response: Response ->
                Log.d(TAG, response.toString())
                Resource.Success(AreaMapper.map(response as HHRegionsResponse))
            },
        )
}
