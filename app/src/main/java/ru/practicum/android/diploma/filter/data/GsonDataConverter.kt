package ru.practicum.android.diploma.filter.data

import com.google.gson.Gson

class GsonDataConverter(
    private val gson: Gson,
) : DataConverter {


    override fun <T> dataToJson(data: T): String =
        gson.toJson(data)

    override fun <T> dataFromJson(json: String, type: Class<T>): T =
        gson.fromJson(json, type)
}