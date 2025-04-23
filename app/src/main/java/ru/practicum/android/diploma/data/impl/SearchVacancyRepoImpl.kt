package ru.practicum.android.diploma.data.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.mapper.VacancyPageResult
import ru.practicum.android.diploma.data.dto.mapper.toDomain
import ru.practicum.android.diploma.data.network.Response
import ru.practicum.android.diploma.data.network.api.NetworkClient
import ru.practicum.android.diploma.data.utils.StringProvider
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.main.VacancyLong
import ru.practicum.android.diploma.domain.repositories.SearchVacancyRepository
import java.io.IOException

class SearchVacancyRepoImpl(
    private val networkClient: NetworkClient,
    private val stringProvider: StringProvider
) : SearchVacancyRepository {
    override fun searchVacancy(vacancyName: String, page: Int, perPage: Int): Flow<Resource<VacancyPageResult>> =
        handleResponse(
            request = {
                networkClient.searchVacancies(
                    mapOf(
                        "text" to vacancyName,
                        "page" to page.toString(),
                        "per_page" to perPage.toString()
                    )
                )
            },
            stringProvider = stringProvider,
            mapper = { dto ->
                VacancyPageResult(
                    vacancies = dto.items.map { it.toDomain() },
                    found = dto.found
                )
            }
        )

    override fun searchVacancyDetails(vacancyId: String): Flow<Resource<VacancyLong>> =
        handleResponse(
            request = { networkClient.getVacancyDetails(vacancyId) },
            stringProvider = stringProvider,
            mapper = { dto -> dto.toDomain() }
        )

    private inline fun <T, R> handleResponse(
        crossinline request: suspend () -> Response<T>,
        stringProvider: StringProvider,
        crossinline mapper: (T) -> R
    ): Flow<Resource<R>> = flow {
        try {
            when (val response = request()) {
                is Response.Success -> emit(Resource.Success(mapper(response.data)))
                else -> emit(Resource.Error(mapError(response, stringProvider)))
            }
        } catch (e: IOException) {
            Log.e("SearchVacancyRepoImpl", "IOException: ${e.message}", e)
            emit(Resource.Error(mapError(Response.NoConnection, stringProvider)))
        } catch (e: HttpException) {
            Log.e("SearchVacancyRepoImpl", "HttpException: ${e.code()} ${e.message}", e)
            emit(Resource.Error(mapError(Response.ServerError, stringProvider)))
        }
    }

    private fun mapError(response: Response<*>, stringProvider: StringProvider): String {
        return when (response) {
            is Response.NoConnection -> stringProvider.getString(R.string.errors_No_connection)
            is Response.BadRequest -> stringProvider.getString(R.string.errors_bad_request)
            is Response.NotFound -> stringProvider.getString(R.string.errors_not_found)
            is Response.ServerError -> stringProvider.getString(R.string.errors_Server)
            else -> stringProvider.getString(R.string.errors_occured)
        }
    }
}
