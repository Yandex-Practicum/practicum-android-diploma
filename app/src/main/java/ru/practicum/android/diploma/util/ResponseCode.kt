package ru.practicum.android.diploma.util

enum class ResponseCode(val code: Int, val description: String) {
    SUCCESS(200, "Запрос выполнен"),
    SERVER_ERROR(500, "Проблемы с сервером"),
    NO_INTERNET(-1, "Отсутствие интернета");

    companion object {
        fun fromCode(code: Int): ResponseCode {
            return entries.find { it.code == code } ?: throw IllegalArgumentException("Unknown code: $code")
        }
    }
}
