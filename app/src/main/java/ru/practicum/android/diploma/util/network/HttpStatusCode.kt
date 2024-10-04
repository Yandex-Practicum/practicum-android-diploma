package ru.practicum.android.diploma.util.network

enum class HttpStatusCode(val code: Int, val description: String) {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
}
