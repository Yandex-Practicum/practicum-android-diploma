package ru.practicum.android.diploma.data.filters.area

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.filters.area.dto.AreaDTO
import ru.practicum.android.diploma.data.filters.area.dto.RegionDTO
import ru.practicum.android.diploma.data.filters.area.network.AreaNetworkClient
import ru.practicum.android.diploma.data.filters.area.network.InnerRegionsRequest
import ru.practicum.android.diploma.data.filters.area.network.RegionListResponse
import ru.practicum.android.diploma.data.filters.area.network.RegionsRequest
import ru.practicum.android.diploma.data.utils.RegionsConverter
import ru.practicum.android.diploma.domain.filters.area.AreaFilterRepository
import ru.practicum.android.diploma.domain.filters.area.model.Region
import ru.practicum.android.diploma.domain.search.Resource

class AreaFilterRepositoryImpl(
    private val networkClient: AreaNetworkClient, private val converter: RegionsConverter
) : AreaFilterRepository {
    override suspend fun getCountriesList(): Flow<Resource<List<Region>>> = flow {
        val request = RegionsRequest(LOCALE_RU)
        val response = networkClient.doRequest(request)
        when (response.resultCode) {
            in CODE_200..CODE_299 -> {
                if (response is RegionListResponse) {
                    val result: List<RegionDTO> = response.regions as List<RegionDTO>
                    val allRegions: List<Region> = result.map {
                        Region(
                            id = it.id,
                            name = it.name,
                            parentId = it.parentId,
                        )
                    }
                    val countries = converter.regionsToCountriesMapper(allRegions)
                    emit(Resource.Success(countries))
                } else {
                    emit(Resource.Error(response.resultCode))
                }
            }

            else -> emit(Resource.Error(response.resultCode))
        }
    }

    override suspend fun getAllRegions(): Flow<Resource<List<Region>>> = flow {
        val request = RegionsRequest(LOCALE_RU)
        val response = networkClient.doRequest(request)
        when (response.resultCode) {
            in CODE_200..CODE_299 -> {
                if (response is RegionListResponse) {
                    val result: List<RegionDTO> = response.regions as List<RegionDTO>
                    val areas: List<AreaDTO> = converter.allInnerRegions(result)
                    val regions = converter.AreaDTOinRegion(areas)
                    emit(Resource.Success(converter.sortByAlfabeth(regions)))
                } else {
                    emit(Resource.Error(response.resultCode))
                }
            }

            else -> emit(Resource.Error(response.resultCode))
        }
    }

    override suspend fun getInnerRegionsList(areaId: Int): Flow<Resource<List<Region>>> = flow {
        val request = InnerRegionsRequest(LOCALE_RU, areaId)
        val response = networkClient.doRequest(request)
        when (response.resultCode) {
            in CODE_200..CODE_299 -> {
                if (response is RegionListResponse) {
                    val result: List<RegionDTO> = response.regions as List<RegionDTO>
                    val innerRegions: List<Region> = result.map {
                        Region(
                            id = it.id,
                            name = it.name,
                            parentId = it.parentId,
                        )
                    }
                    emit(Resource.Success(innerRegions))
                } else {
                    emit(Resource.Error(response.resultCode))
                }
            }

            else -> emit(Resource.Error(response.resultCode))
        }
    }

    private companion object {
        const val CODE_299 = 299
        const val CODE_200 = 200
        const val LOCALE_RU = "RU"
    }
}
