package ru.practicum.android.diploma.industry.data

import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.core.data.network.Request
import ru.practicum.android.diploma.core.data.network.ResultCode
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.industry.data.dto.IndustriesDto
import ru.practicum.android.diploma.industry.domain.api.IndustriesRepository
import ru.practicum.android.diploma.industry.domain.models.Industries
import ru.practicum.android.diploma.industry.domain.models.Industry

class IndustriesRepositoryImpl(private val networkClient: NetworkClient) : IndustriesRepository {
    private var _items: Industries? = null
    override suspend fun getIndustries(query: String): Resource<Industries> {
        if (_items != null) {
            return Resource.Success(filter(_items!!, query))
        } else {
            val response = networkClient.doRequest(Request.IndustriesRequest)

            return when (response.resultCode) {
                ResultCode.SUCCESS -> {
                    val dto = response.data as IndustriesDto
                    _items = mapping(dto)
                    Resource.Success(filter(_items!!, query))
                }

                else -> {
                    Resource.Error(
                        code = response.resultCode
                    )
                }
            }
        }
    }

    private fun mapping(dto: IndustriesDto): Industries {
        return dto.map { item ->
            Industry(item.id, item.name)
        }
    }

    private fun filter(items: List<Industry>, query: String): Industries {
        return if (query.isEmpty()) {
            items
        } else {
            items.filter {
                it.name.lowercase().contains(query.lowercase().trim())
            }
        }
    }
}
