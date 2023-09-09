package ru.practicum.android.diploma.util.functional

import ru.practicum.android.diploma.di.annotations.NewResponse

@NewResponse
sealed class Failure(val code: Int) {
    class Offline(code: Int = DISCONNECTED_CODE) : Failure(code)
    class ServerError(code: Int) : Failure(code)
    class AppFailure(code: Int = APP_FAILURE_CODE) : Failure(code)
    class NotFound(code: Int = NOTHING_FOUND_CODE): Failure(code = code)
}

private const val NOTHING_FOUND_CODE = -3
private const val DISCONNECTED_CODE = -1
private const val APP_FAILURE_CODE = -2