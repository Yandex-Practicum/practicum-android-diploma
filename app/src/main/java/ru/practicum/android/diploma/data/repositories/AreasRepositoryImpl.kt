package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.AreaResponseDto
import ru.practicum.android.diploma.data.network.DiplomaApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.AreasRepository
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.util.Resource

class AreasRepositoryImpl(
    private val api: DiplomaApi,
    private val networkClient: NetworkClient
) : AreasRepository {

    override suspend fun getCountries(): Resource<List<Country>> = withContext(Dispatchers.IO) {
        when (val result = networkClient.safeApiCall { api.getAreas() }) {
            is Resource.Success -> {
                val countries = result.data
                    .filter { it.parentId.isNullOrEmpty() && it.id != OTHER_REGIONS_ID }
                    .map { Country(id = it.id, name = it.name) }
                Resource.Success(countries)
            }
            is Resource.Error -> Resource.Error(message = result.message, code = result.code)
            Resource.Loading -> Resource.Loading
        }
    }

    override suspend fun getRegions(countryId: String?): Resource<List<Region>> = withContext(Dispatchers.IO) {
        when (val result = networkClient.safeApiCall { api.getAreas() }) {
            is Resource.Success -> {
                val regions = mutableListOf<Region>()
                if (countryId != null) {
                    val countryDto = result.data.firstOrNull { it.id == countryId }
                    if (countryDto != null) {
                        regions.addAll(flattenRegions(countryDto, countryDto.id, countryDto.name))
                    }
                } else {
                    for (countryDto in result.data) {
                        if (countryDto.id == OTHER_REGIONS_ID) {
                            for (otherCountry in countryDto.areas) {
                                regions.addAll(flattenRegions(otherCountry, otherCountry.id, otherCountry.name))
                            }
                        } else {
                            regions.addAll(flattenRegions(countryDto, countryDto.id, countryDto.name))
                        }
                    }
                }
                regions.sortBy { it.name }
                Resource.Success(regions)
            }
            is Resource.Error -> Resource.Error(message = result.message, code = result.code)
            Resource.Loading -> Resource.Loading
        }
    }

    private fun flattenRegions(area: AreaResponseDto, countryId: String, countryName: String): List<Region> {
        val result = mutableListOf<Region>()
        if (!area.parentId.isNullOrEmpty()) {
            result.add(Region(id = area.id, name = area.name, countryId = countryId, countryName = countryName))
        }
        for (child in area.areas) {
            result.addAll(flattenRegions(child, countryId, countryName))
        }
        return result
    }

    companion object {
        private const val OTHER_REGIONS_ID = "1001"
    }
}
