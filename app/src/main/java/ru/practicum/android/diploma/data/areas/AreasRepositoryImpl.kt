package ru.practicum.android.diploma.data.areas

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.AreaConverter
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.AreasDto
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.areas.api.AreasRepository
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Places
import ru.practicum.android.diploma.util.ResponseCode

class AreasRepositoryImpl(
    private val networkClient: NetworkClient,
    private val areaConverter: AreaConverter,
) : AreasRepository {

    override suspend fun getCountries(): Resource<List<Area>> {
        val response = networkClient.doRequestAreas()
        when (response.resultCode) {
            ResponseCode.SUCCESS.code -> {
                with(response as AreasDto) {
                    val (countries, _) = splitAreas(response)
                    val data = areaConverter.convertCountries(countries)
                    return Resource.Success(data)
                }
            }

            else -> return Resource.Error(response.resultCode)

        }
    }

    override suspend fun getAreasByCountry(countryId: String): Resource<Places> {
        val response = networkClient.doRequestArea(countryId)

        when (response.resultCode) {
            ResponseCode.SUCCESS.code -> {
                with(response as AreaDto) {
                    val (countries, otherAreas) = splitAreas(AreasDto(areas = listOf(response)))
                    val data = areaConverter.convertAreas(countries, otherAreas.sortedBy { it.name })
                    return Resource.Success(data)
                }
            }

            else -> return Resource.Error(response.resultCode)

        }
    }

    override suspend fun getAllAreas(): Resource<Places> {
        val response = networkClient.doRequestAreas()

        when (response.resultCode) {
            ResponseCode.SUCCESS.code -> {
                with(response as AreasDto) {
                    val (countries, otherAreas) = splitAreas(response)
                    val data = areaConverter.convertAreas(countries, otherAreas.sortedBy { it.name })
                    return Resource.Success(data)
                }
            }

            else -> return Resource.Error(response.resultCode)

        }
    }

    private fun splitAreas(response: AreasDto): Pair<List<AreaDto>, List<AreaDto>> {
        // преобразуем дерево регионов в плоский список
        var toProceed = response.areas
        val allAreas = mutableListOf<AreaDto>()

        while (toProceed.isNotEmpty()) {
            allAreas += toProceed
            toProceed = toProceed.flatMap { it.areas }
        }

        // делим на страны и не-страны
        val countries = allAreas.filter { it.parentId == null }
        val otherAreas = allAreas.filter { it.parentId != null }

        return Pair(countries, otherAreas)
    }
}
