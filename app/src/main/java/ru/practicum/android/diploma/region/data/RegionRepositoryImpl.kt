package ru.practicum.android.diploma.region.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.data.dto.AreaDto
import ru.practicum.android.diploma.core.data.dto.AreasDto
import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.core.data.network.Request
import ru.practicum.android.diploma.core.data.network.ResultCode
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.region.domain.api.RegionRepository
import ru.practicum.android.diploma.region.domain.models.RegionItem

class RegionRepositoryImpl(private val networkClient: NetworkClient) : RegionRepository {
    private var areasDto: AreasDto? = null

    override fun getRegions(countryId: String?): Flow<Resource<List<RegionItem>>> = flow {
        if (!areasDto.isNullOrEmpty()) {
            emit(Resource.Success(mapRegions(areasDto.orEmpty(), countryId)))
        } else {
            emit(Resource.Loading)

            val response = networkClient.doRequest(Request.AreasRequest)

            when (response.resultCode) {
                ResultCode.SUCCESS -> {
                    areasDto = response.data as? AreasDto
                    emit(Resource.Success(mapRegions(areasDto.orEmpty(), countryId)))
                }

                else -> emit(Resource.Error(response.resultCode))
            }
        }
    }

    private fun mapRegions(tree: AreasDto, countryId: String?): List<RegionItem> {
        val countries = tree.filter { it.parentId == null }
        val roots = if (countryId != null) countries.filter { it.id == countryId } else countries
        return roots
            .flatMap { country -> collectRegions(country.areas, Area(country.id, country.name)) }
            .sortedBy { it.region.name.lowercase() }
    }

    private fun collectRegions(nodes: List<AreaDto>, country: Area): List<RegionItem> {
        return nodes.flatMap { node ->
            buildList {
                if (node.parentId != null) {
                    add(RegionItem(region = Area(node.id, node.name), country = country))
                }
                addAll(collectRegions(node.areas, country))
            }
        }
    }
}
