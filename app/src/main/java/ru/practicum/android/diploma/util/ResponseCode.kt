package ru.practicum.android.diploma.util

private const val SUCCESS_CODE: Int = 200
private const val SERVER_ERROR_CODE: Int = 500
private const val NO_INTERNET_CODE: Int = -1
private const val NOT_FOUND_CODE = 404
private const val DATABASE_ERROR_CODE = 999

enum class ResponseCode(val code: Int, val description: String) {

    SUCCESS(SUCCESS_CODE, "Запрос выполнен"),
    SERVER_ERROR(SERVER_ERROR_CODE, "Проблемы с сервером"),
    NO_INTERNET(NO_INTERNET_CODE, "Отсутствие интернета"),
    NOT_FOUND(NOT_FOUND_CODE, "Данные не найдены"),
    DATABASE_ERROR(DATABASE_ERROR_CODE, "Ошибка базы данных");

    companion object {
        fun fromCode(code: Int): ResponseCode {
            return entries.find { it.code == code }
                ?: throw IllegalArgumentException("Unknown code: $code")
        }
    }
}
