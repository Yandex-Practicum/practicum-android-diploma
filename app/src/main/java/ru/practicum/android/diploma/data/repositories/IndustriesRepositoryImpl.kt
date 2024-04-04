package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.filter.industries.IndustriesRequest
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.vacancies.response.ResponseCodes
import ru.practicum.android.diploma.domain.industries.IndustriesRepository
import ru.practicum.android.diploma.util.ResourceContentSearch
import java.io.IOException
import ru.practicum.android.diploma.data.converter.IndustriesConverter.convert
import ru.practicum.android.diploma.data.filter.industries.IndustriesResponse
import ru.practicum.android.diploma.domain.models.industries.ChildIndustry

class IndustriesRepositoryImpl(
    private val networkClient: NetworkClient
) : IndustriesRepository {

    override fun getIndustries(): Flow<ResourceContentSearch<List<ChildIndustry>>> = flow {
        val response = networkClient.doRequestFilter(IndustriesRequest)
        when (response.resultCode) {

            ResponseCodes.DEFAULT
            -> emit(ResourceContentSearch.ErrorSearch(response.resultCode.code))

            ResponseCodes.ERROR
            -> emit(ResourceContentSearch.ErrorSearch(response.resultCode.code))

            ResponseCodes.NO_CONNECTION
            -> emit(ResourceContentSearch.ErrorSearch(response.resultCode.code))

            ResponseCodes.SERVER_ERROR
            -> emit(ResourceContentSearch.ErrorSearch(response.resultCode.code))

            ResponseCodes.SUCCESS -> {
                try {
                    emit(
                        ResourceContentSearch.SuccessSearch(
                            (response as IndustriesResponse).industries
                                .convert()
                                .sortedBy { it.name }
                        )
                    )
                } catch (e: IOException) {
                    emit(ResourceContentSearch.ErrorSearch(response.resultCode.code))
                    throw e
                }
            }
        }

    }

}
