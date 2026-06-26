package ru.practicum.android.diploma.data.network.models

enum class HttpErrorType {
    NETWORK,
    CLIENT,
    SERVER,
    UNKNOWN;

    companion object {
        const val NO_CONNECTION_CODE = -1
        const val CLIENT_ERROR_START = 400
        const val CLIENT_ERROR_END = 499
        const val SERVER_ERROR_CODE = 500
    }
}

fun Int.toHttpErrorType(): HttpErrorType = when (this) {
    HttpErrorType.NO_CONNECTION_CODE -> HttpErrorType.NETWORK
    in HttpErrorType.CLIENT_ERROR_START..HttpErrorType.CLIENT_ERROR_END -> HttpErrorType.CLIENT
    HttpErrorType.SERVER_ERROR_CODE -> HttpErrorType.SERVER
    else -> HttpErrorType.UNKNOWN
}
