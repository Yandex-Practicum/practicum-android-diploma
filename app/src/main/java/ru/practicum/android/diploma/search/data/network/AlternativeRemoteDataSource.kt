package ru.practicum.android.diploma.search.data.network


import ru.practicum.android.diploma.search.data.network.dto.request.Request
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

//@NewResponse
interface AlternativeRemoteDataSource {
    suspend fun doRequest(request: Request): Either<Failure, Any>
}