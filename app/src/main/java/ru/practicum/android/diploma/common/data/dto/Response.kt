package ru.practicum.android.diploma.common.data.dto

open class Response {
    var resultCode = 0

    companion object {
        const val NO_INTERNET_ERROR_CODE = -1
        const val SUCCESS_RESPONSE_CODE = 200
        const val BAD_REQUEST_ERROR_CODE = 400
        const val NOT_FOUND_ERROR_CODE = 404
        const val INTERNAL_SERVER_ERROR_CODE = 500
    }
}
