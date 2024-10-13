package ru.practicum.android.diploma.util.network

enum class HttpStatusCode(resultCode: Int) {
    DUMMY(resultCode = 0),
    NOT_CONNECTED(resultCode = -1),
    OK(resultCode = 200),
    BAD_REQUEST(resultCode = 400),
    FORBIDDEN(resultCode = 403),
    NOT_FOUND(resultCode = 404),
    INTERNAL_SERVER_ERROR(resultCode = 500),
}
