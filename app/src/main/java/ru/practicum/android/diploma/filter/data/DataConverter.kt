package ru.practicum.android.diploma.filter.data

interface DataConverter {
    fun <T> dataToJson(data: T): String
    fun <T> dataFromJson(json: String, type: Class<T>): T
}