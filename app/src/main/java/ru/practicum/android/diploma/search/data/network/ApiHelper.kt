package ru.practicum.android.diploma.search.data.network

import android.util.Log
import retrofit2.Response
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.data.model.CountryDto
import ru.practicum.android.diploma.filter.data.model.IndustryDto
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesResponse
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

//@NewResponse
class ApiHelper @Inject constructor(
    private val apiService: HhApiService,
    private val networkHandler: InternetController,
    private val logger: Logger
):  AlternativeRemoteDataSource{
    override suspend fun getVacancies(query: String): Either<Failure, VacanciesResponse> {
        return requestData(VacanciesResponse.empty) {
            apiService.searchVacancies(query)
        }
    }

    override suspend fun getAllCountries(): Either<Failure, List<CountryDto>> {
        return requestData(emptyList()) {
            apiService.getAllCountries()
        }
    }

    override suspend fun getIndustries(): Either<Failure, List<IndustryDto>> {
        return requestData(emptyList()) {
            apiService.getIndustries()
        }
    }

    private fun <T> responseHandle(
        response: Response<T>,
        default: T,
        isConnected: Boolean
    ): Either<Failure, T> {
        return when (response.isSuccessful) {
            true -> {
                logger.log(thisName,
                    "responseHandle: SUCCESS code= ${response.code()}; from cache = ${!isConnected}")
                Either.Right(response.body() ?: default)
            }
            false -> {
                if (isConnected) {
                    logger.log(thisName, "responseHandle: FAILURE code= ${response.code()}")
                    Either.Left(Failure.ServerError(response.code()))
                } else {
                    logger.log(thisName, "responseHandle: NOT CONNECTED")
                    Either.Left(Failure.NetworkConnection())
                }
            }
        }
    }

    private suspend fun <T> requestData(default: T, request: suspend () -> Response<T>) =
        when (networkHandler.isInternetAvailable()) {
            true -> {
                try {
                    responseHandle(request(), default, true)
                } catch (exception: Throwable) {
                    Either.Left(Failure.AppFailure())
                }
            }
            false -> responseHandle(request(), default, false)
        }
}