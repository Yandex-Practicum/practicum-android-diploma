package ru.practicum.android.diploma.search.data.network

import retrofit2.Response
import ru.practicum.android.diploma.di.annotations.NewResponse
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesResponse
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import javax.inject.Inject

@NewResponse
class ApiHelper @Inject constructor(
    private val apiService: HhApiService,
    private val networkHandler: InternetController
):  AlternativeRemoteDataSource{
    override suspend fun getVacancies(query: String): Either<Failure, VacanciesResponse> {
        return requestData(VacanciesResponse.empty) {
            apiService.searchVacancies(query)
        }
    }

    private fun <T> responseHandle(response: Response<T>, default: T): Either<Failure, T> =
        when (response.isSuccessful) {
            true -> Either.Right(response.body() ?: default)
            false -> Either.Left(Failure.ServerError(response.code()))
        }


    private suspend fun <T> requestData(default: T, request: suspend () -> Response<T>) =
        when (networkHandler.isInternetAvailable()) {
            true -> {
                try {
                    responseHandle(request(), default)
                } catch (exception: Throwable) {
                    Either.Left(Failure.ServerError(111))
                }
            }
            false -> Either.Left(Failure.NetworkConnection(555))
        }
}