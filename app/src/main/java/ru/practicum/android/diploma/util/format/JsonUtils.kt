package ru.practicum.android.diploma.util.format

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonUtils {

    val gson = Gson()

    fun <T> toJson(data: T): String {
        return gson.toJson(data)
    }

    fun <T> fromJson(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }

    inline fun <reified T> fromJsonList(json: String): List<T> {
        val type = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(json, type)
    }

    inline fun <reified T> deserializeField(json: String?, clazz: Class<T>): T? {
        return json?.let { fromJson(it, clazz) }
    }
}
