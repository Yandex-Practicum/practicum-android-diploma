package ru.practicum.android.diploma.filter.data.converter

import com.google.gson.Gson
import java.lang.reflect.Type
import javax.inject.Inject

class GsonDataConverter @Inject constructor(
    private val gson: Gson,
) : DataConverter {
    
    override fun <T> dataToJson(data: T): String =
        gson.toJson(data)

    override fun <T> dataFromJson(json: String, type: Type): T =
        gson.fromJson(json, type)
}