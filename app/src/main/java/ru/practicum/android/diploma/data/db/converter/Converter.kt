package ru.practicum.android.diploma.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.entity.FilterAreaEmbedded
import ru.practicum.android.diploma.data.db.entity.PhoneEmbedded

class Converter {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let(gson::toJson)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let { gson.fromJson(it, object : TypeToken<List<String>>() {}.type) }
    }

    @TypeConverter
    fun fromPhonesList(value: List<PhoneEmbedded>?): String? {
        return value?.let(gson::toJson)
    }

    @TypeConverter
    fun toPhonesList(value: String?): List<PhoneEmbedded>? {
        return value?.let { gson.fromJson(it, object : TypeToken<List<PhoneEmbedded>>() {}.type) }
    }

    @TypeConverter
    fun fromFilterArea(value: List<FilterAreaEmbedded>?): String? {
        return value?.let(gson::toJson)
    }

    @TypeConverter
    fun toFilterArea(value: String?): List<FilterAreaEmbedded>? {
        return value?.let { gson.fromJson(it, object : TypeToken<List<FilterAreaEmbedded>>() {}.type) }
    }
}
