package ru.practicum.android.diploma.util.functional

import ru.practicum.android.diploma.di.annotations.NewResponse

@NewResponse
sealed class Failure(val code: Int) {
    class NetworkConnection(code: Int) : Failure(code)
    class ServerError(code: Int) : Failure(code)
    abstract class AppFailure(code: Int) : Failure(code)

}