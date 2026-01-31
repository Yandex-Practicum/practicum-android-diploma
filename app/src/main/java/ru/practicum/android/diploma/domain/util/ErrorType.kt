package ru.practicum.android.diploma.domain.util

import ru.practicum.android.diploma.data.network.NetworkCodes

enum class ErrorType {
    NO_INTERNET,
    SERVER_ERROR,
    TIMEOUT,
    NOT_FOUND;

    companion object {
        fun fromCode(code: Int): ErrorType = when (code) {
            NetworkCodes.NO_NETWORK_CODE -> NO_INTERNET
            NetworkCodes.SERVER_ERROR_CODE -> SERVER_ERROR
            NetworkCodes.TIMEOUT_CODE -> TIMEOUT
            NetworkCodes.NOT_FOUND_CODE -> NOT_FOUND
            else -> NOT_FOUND
        }
    }
}
