package ru.practicum.android.diploma.filter.industry.data.repositoryimpl

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.commonutils.network.Response
import ru.practicum.android.diploma.commonutils.network.executeNetworkRequest
import ru.practicum.android.diploma.data.networkclient.api.NetworkClient
import ru.practicum.android.diploma.data.networkclient.api.dto.request.HHApiIndustriesRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.response.industries.HHIndustriesResponse
import ru.practicum.android.diploma.filter.industry.data.mappers.IndustryMapper
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.industry.domain.repository.IndustryRepository

internal class IndustryRepositoryImpl(
    private val networkClient: NetworkClient,
    private val industryMapper: IndustryMapper,
    private val context: Context,
) : IndustryRepository {
    override fun getIndustriesList(): Flow<Resource<List<IndustryModel>>> =
        context.executeNetworkRequest(
            request = { networkClient.doRequest(HHApiIndustriesRequest) },
            successHandler = { response: Response ->
                Log.d(TAG, response.toString())
                Resource.Success(industryMapper.map(response as HHIndustriesResponse))
            },
        )

    companion object {
        private const val TAG = "IndustryRepositoryImpl"
    }
}
