package ru.practicum.android.diploma.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromString(value: String?): Array<String?>? {
        return if (value == null) {
            null
        } else {
            Gson().fromJson(value, object : TypeToken<Array<String?>?>() {}.type)
        }
    }

    @TypeConverter
    fun toString(array: Array<String?>?): String? {
        return Gson().toJson(array)
    }
}
