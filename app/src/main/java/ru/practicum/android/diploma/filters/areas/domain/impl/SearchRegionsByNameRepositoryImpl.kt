package ru.practicum.android.diploma.filters.areas.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filters.areas.domain.api.SearchRegionsByNameRepository
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.network.HttpStatusCode
import ru.practicum.android.diploma.util.network.NetworkClient
import ru.practicum.android.diploma.util.network.SearchRegionsByNameRequest
import ru.practicum.android.diploma.util.network.SearchRegionsByNameResponse

class SearchRegionsByNameRepositoryImpl(
    private val networkClient: NetworkClient,
    private val converter: RegionToAreaConverter
) : SearchRegionsByNameRepository {

    private val textAndParentId: HashMap<String, String> = HashMap()

    override fun setText(text: String) {
        textAndParentId["text"] = text
    }

    override fun setParentId(parentId: String) {
        textAndParentId["parentId"] = parentId
    }

    override fun searchRegionsByName(): Flow<Resource<List<Area>>> = flow {
        val response = networkClient.doRequest(SearchRegionsByNameRequest(textAndParentId))
        emit(
            when (response.resultCode) {
                HttpStatusCode.OK -> {
                    val regions = (response as SearchRegionsByNameResponse).listWithRegionInformation.map {
                        converter.map(it)
                    }

                    if (regions.isEmpty()) {
                        Resource.Error(HttpStatusCode.NOT_FOUND, null)
                    } else {
                        Resource.Success(regions)
                    }
                }

                HttpStatusCode.NOT_CONNECTED -> {
                    Resource.Error(HttpStatusCode.NOT_CONNECTED, null)
                }

                else -> {
                    Resource.Error(response.resultCode)
                }
            }
        )
    }
}
