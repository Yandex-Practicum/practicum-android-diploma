package ru.practicum.android.diploma.util.functional

sealed class Failure(val code: Int) {
    class Offline(code: Int = DISCONNECTED_CODE) : Failure(code = code)
    class ServerError(code: Int) : Failure(code = code)
    class AppFailure(code: Int = APP_FAILURE_CODE) : Failure(code = code)
    class NotFound(code: Int = NOTHING_FOUND_CODE): Failure(code = code)
}

private const val NOTHING_FOUND_CODE = -3
private const val DISCONNECTED_CODE = -1
private const val APP_FAILURE_CODE = -2