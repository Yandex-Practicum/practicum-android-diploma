package ru.practicum.android.diploma.util.storage

enum class ResponseCode(val code: Int) {
    RESPONSE_OK(200),
    RESPONSE_BAD_REQUEST(400),
    RESPONSE_NOT_CONNECTED(-1),
    RESPONSE_INTERNAL_SERVER_ERROR(500)
}
