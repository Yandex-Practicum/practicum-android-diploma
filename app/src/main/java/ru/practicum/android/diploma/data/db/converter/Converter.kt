package ru.practicum.android.diploma.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.entity.FilterAreaEmbedded
import ru.practicum.android.diploma.data.db.entity.PhoneEmbedded

class Converter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: List<String>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toString(value: String?): List<String>? {
        return gson.fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromPhonesList(value: List<PhoneEmbedded>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPhonesList(value: String?): List<PhoneEmbedded>? {
        return gson.fromJson(value, object : TypeToken<List<PhoneEmbedded>>() {}.type)
    }

    @TypeConverter
    fun fromFilterArea(value: List<FilterAreaEmbedded>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toFilterArea(value: String?): List<FilterAreaEmbedded>? {
        return gson.fromJson(value, object : TypeToken<List<FilterAreaEmbedded>>() {}.type)
    }
}
