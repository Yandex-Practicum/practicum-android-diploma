package ru.practicum.android.diploma.util.network

enum class HttpStatusCode(resultCode: Int) {
    OK(resultCode = 200),
    BAD_REQUEST(resultCode = 400),
    FORBIDDEN(resultCode = 403),
    NOT_FOUND(resultCode = 404)
}
