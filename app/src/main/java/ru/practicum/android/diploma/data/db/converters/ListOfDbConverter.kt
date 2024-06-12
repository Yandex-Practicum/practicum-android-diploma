package ru.practicum.android.diploma.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.dao.models.PhoneEntity

class ListOfDbConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromPhoneList(phones: List<PhoneEntity?>?): String? {
        if (phones == null) return null
        val gson = Gson()
        val type = object : TypeToken<List<PhoneEntity?>>() {}.type
        return gson.toJson(phones, type)
    }

    @TypeConverter
    fun toPhoneList(phoneString: String?): List<PhoneEntity?>? {
        if (phoneString == null) return null
        val gson = Gson()
        val type = object : TypeToken<List<PhoneEntity?>>() {}.type
        return gson.fromJson(phoneString, type)
    }
}
