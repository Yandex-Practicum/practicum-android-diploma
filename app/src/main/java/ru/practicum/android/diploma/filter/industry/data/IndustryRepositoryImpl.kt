package ru.practicum.android.diploma.filter.industry.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.data.NetworkClient
import ru.practicum.android.diploma.core.data.mapper.mapToDomain
import ru.practicum.android.diploma.core.data.network.dto.GetIndustriesResponse
import ru.practicum.android.diploma.filter.industry.domain.api.IndustryRepository
import ru.practicum.android.diploma.filter.industry.domain.model.Industry
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryError
import ru.practicum.android.diploma.util.Result

class IndustryRepositoryImpl(
    private val networkClient: NetworkClient
) : IndustryRepository {
    private val runtimeSavedIndustries = mutableListOf<Industry>()

    override fun getIndustries(): Flow<Result<List<Industry>, IndustryError>> = flow {
        if (runtimeSavedIndustries.isEmpty()) {
            getIndustriesFromNetwork().collect(this)
        } else {
            emit(Result.Success(runtimeSavedIndustries))
        }
    }

    private fun getIndustriesFromNetwork(): Flow<Result<List<Industry>, IndustryError>> = flow {
        val response = networkClient.getIndustries()
        when (response.resultCode) {
            NetworkClient.SUCCESSFUL_CODE -> {
                val data = (response as GetIndustriesResponse).industries.flatMap {
                    it.industries ?: listOf(it)
                }
                runtimeSavedIndustries.clear()
                runtimeSavedIndustries.addAll(data.mapToDomain())
                emit(Result.Success(data.mapToDomain()))
            }

            else -> {
                emit(Result.Error(IndustryError.GetError))
            }
        }
    }
}
