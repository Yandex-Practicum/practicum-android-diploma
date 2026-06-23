package ru.practicum.android.diploma.data.network.models

enum class HttpErrorType {
    NETWORK,
    CLIENT,
    SERVER,
    UNKNOWN
}

fun Int.toHttpErrorType(): HttpErrorType = when (this) {
    -1 -> HttpErrorType.NETWORK
    in 400..499 -> HttpErrorType.CLIENT
    500 -> HttpErrorType.SERVER
    else -> HttpErrorType.UNKNOWN
}
