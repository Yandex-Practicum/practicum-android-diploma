package ru.practicum.android.diploma.filters.industries.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filters.industries.data.dto.FilterIndustriesRequest
import ru.practicum.android.diploma.filters.industries.data.dto.FilterIndustriesResponse
import ru.practicum.android.diploma.filters.industries.domain.api.FilterIndustriesRepository
import ru.practicum.android.diploma.filters.industries.domain.models.Industry
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.network.HttpStatusCode
import ru.practicum.android.diploma.util.network.NetworkClient

class FilterIndustriesRepositoryImpl(
    private val networkClient: NetworkClient
) : FilterIndustriesRepository {
    override fun getIndustries(): Flow<Resource<List<Industry>>> = flow {
        val response = networkClient.doRequest(FilterIndustriesRequest())
        emit(
            when (response.resultCode) {
                HttpStatusCode.OK -> {
                    val industries = (response as FilterIndustriesResponse)
                        .industries.map { industry ->
                            Industry(
                                id = industry.id,
                                name = industry.name,
                                isChecked = false,
                                industries = industry.industries?.map { subIndustri ->
                                    Industry(
                                        id = subIndustri.id,
                                        name = subIndustri.name,
                                        isChecked = false,
                                        industries = null
                                    )
                                }
                            )
                        }

                    Resource.Success(getAllIndustries(industries))
                }

                else -> Resource.Error(response.resultCode)
            }
        )
    }

    private fun getAllIndustries(industries: List<Industry>): List<Industry> {
        val allIndustries = mutableListOf<Industry>()
        industries.forEach { industry ->
            allIndustries.add(industry)
            industry.industries?.let {
                allIndustries.addAll(it)
            }
        }
        return allIndustries.sortedBy { it.name }
    }
}
