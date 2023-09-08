package ru.practicum.android.diploma.util.functional

import ru.practicum.android.diploma.di.annotations.NewResponse

@NewResponse
sealed class Failure(val code: Int) {
    class NetworkConnection(code: Int = 408) : Failure(code)
    class ServerError(code: Int) : Failure(code)
    class UnknownError(code: Int = 111): Failure(code)
    abstract class AppFailure(code: Int) : Failure(code)
    class NotFound(code: Int = 204): Failure(code = code)

}