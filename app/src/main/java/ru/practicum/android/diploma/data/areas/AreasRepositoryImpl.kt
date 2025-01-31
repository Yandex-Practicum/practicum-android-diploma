package ru.practicum.android.diploma.data.areas

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.AreaConverter
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.AreasDto
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.areas.api.AreasRepository
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.util.ResponseCode

class AreasRepositoryImpl(
    private val networkClient: NetworkClient,
    private val areaConverter: AreaConverter,
) : AreasRepository {

    override suspend fun getCountries(): Resource<List<Area>> {
        val response = networkClient.doRequestCountries()
        when (response.resultCode) {
            ResponseCode.SUCCESS.code -> {
                with(response as AreasDto) {
                    val data = areaConverter.convertCountries(response)
                    return Resource.Success(data)
                }
            }

            else -> return Resource.Error(response.resultCode)

        }
    }

    override suspend fun getRegions(countryId: String): Resource<List<Area>> {
        val response = networkClient.doRequestArea(countryId)

        when (response.resultCode) {
            ResponseCode.SUCCESS.code -> {
                with(response as AreaDto) {
                    val data = areaConverter.convertRegions(response)
                    return Resource.Success(data)
                }
            }

            else -> return Resource.Error(response.resultCode)

        }
    }
}
