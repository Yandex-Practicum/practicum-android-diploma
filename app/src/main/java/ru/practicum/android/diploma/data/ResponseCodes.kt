package ru.practicum.android.diploma.data

enum class ResponseCodes(val code: Int) {
    NO_CONNECTION(-1),
    SUCCESS(200),
    ERROR(400)
}
