package ru.practicum.android.diploma.data.vacancysearchscreen.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.mappers.toDataRequest
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.models.vacancies.VacanciesRequest
import ru.practicum.android.diploma.data.models.vacancies.VacanciesResponseDto
import ru.practicum.android.diploma.data.models.vacancydetails.VacancyDetailsRequest
import ru.practicum.android.diploma.data.models.vacancydetails.VacancyDetailsResponseDto
import ru.practicum.android.diploma.data.vacancysearchscreen.network.NetworkClient
import ru.practicum.android.diploma.domain.models.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.filters.VacancyFilters
import ru.practicum.android.diploma.domain.models.paging.VacanciesResult
import ru.practicum.android.diploma.domain.models.vacancydetails.VacancyDetails
import ru.practicum.android.diploma.util.DebounceConstants.NO_CONNECTION
import ru.practicum.android.diploma.util.DebounceConstants.SEARCH_SUCCESS
import ru.practicum.android.diploma.util.DebounceConstants.SERVER_ERROR
import ru.practicum.android.diploma.util.Resource

class VacanciesRepositoryImpl(private val networkClient: NetworkClient) : VacanciesRepository {

    private val loadedPages = mutableSetOf<String>()
    private var lastRequestKeyPrefix: String? = null

    override fun search(vacancy: VacancyFilters): Flow<Resource<VacanciesResult>> = flow {
        val request = vacancy.toDataRequest()

        val requestKey = buildRequestKey(request)

        val currentPrefixKey = buildRequestKeyPrefix(request)
        if (currentPrefixKey != lastRequestKeyPrefix) {
            loadedPages.clear()
            lastRequestKeyPrefix = currentPrefixKey
        }

        if (requestKey in loadedPages) {
            emit(Resource.Success(VacanciesResult(emptyList(), request.page, 0, 0)))
            return@flow
        }

        try {
            val response = networkClient.doRequest(request)
            when (response.resultCode) {
                SEARCH_SUCCESS -> {
                    val dto = response as VacanciesResponseDto
                    loadedPages.add(requestKey)

                    val vacancies = dto.items.map { it.toDomain() }
                    emit(
                        Resource.Success(
                            VacanciesResult(
                                vacancies = vacancies,
                                page = dto.page,
                                pages = dto.pages,
                                totalFound = dto.found
                            )
                        )
                    )
                }

                NO_CONNECTION -> emit(Resource.Error(ErrorType.NO_INTERNET))
                SERVER_ERROR -> emit(Resource.Error(ErrorType.SERVER_ERROR))
                else -> emit(Resource.Error(ErrorType.UNKNOWN))
            }
        } catch (e: retrofit2.HttpException) {
            Log.e("Repository", "HTTP error in search", e)
            throw e
        }
    }

    override fun getVacancyDetailsById(id: String): Flow<Resource<VacancyDetails>> = flow {
        val response = networkClient.doRequest(VacancyDetailsRequest(id))
        when (response.resultCode) {
            SEARCH_SUCCESS -> {
                val data = (response as VacancyDetailsResponseDto).toDomain()
                emit(Resource.Success(data))
            }

            NO_CONNECTION -> emit(Resource.Error(ErrorType.NO_INTERNET))
            SERVER_ERROR -> emit(Resource.Error(ErrorType.SERVER_ERROR))
            else -> emit(Resource.Error(ErrorType.UNKNOWN))
        }
    }

    override fun clearLoadedPages() {
        loadedPages.clear()
    }

    private fun buildRequestKey(request: VacanciesRequest): String {
        return buildString {
            append("page:${request.page}")
            append("|text:${request.text}")
            request.area?.let { append("|area:$it") }
            request.industry?.let { append("|industry:$it") }
            request.currency?.let { append("|currency:$it") }
            request.salary?.let { append("|salary:$it") }
        }
    }

    private fun buildRequestKeyPrefix(request: VacanciesRequest): String {
        return buildString {
            append("text:${request.text}")
            request.area?.let { append("|area:$it") }
            request.industry?.let { append("|industry:$it") }
            request.currency?.let { append("|currency:$it") }
            request.salary?.let { append("|salary:$it") }
        }
    }
}

enum class ErrorType {
    NO_INTERNET,
    SERVER_ERROR,
    UNKNOWN
}
