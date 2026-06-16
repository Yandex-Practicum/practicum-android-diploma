package ru.practicum.android.diploma.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

open class PhoneListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromJson(value: String?): List<Phone> {
        return if (value.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<Phone>>() {}.type
            gson.fromJson(value, type)
        }
    }

    @TypeConverter
    fun toJson(list: List<Phone>): String {
        return gson.toJson(list)
    }
}
