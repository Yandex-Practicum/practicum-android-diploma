package ru.practicum.android.diploma.data

enum class ResponseCodes(val code: Int) {
    DEFAULT(0),
    NO_CONNECTION(-1),
    SUCCESS(200),
    ERROR(400),
    SERVER_ERROR(500)
}
