package ru.practicum.android.diploma.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {
    @TypeConverter
    fun stringToList(listOfString: String): List<String> {
        return Gson().fromJson(listOfString, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun listToString(list: List<String>): String {
        return Gson().toJson(list)
    }
}
