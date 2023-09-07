package ru.practicum.android.diploma.filter.data.converter

import java.lang.reflect.Type

interface DataConverter {

    fun <T> dataToJson(data: T): String
    fun <T> dataFromJson(json: String, type: Type): T
}