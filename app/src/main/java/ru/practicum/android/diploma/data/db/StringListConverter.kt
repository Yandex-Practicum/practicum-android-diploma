package ru.practicum.android.diploma.data.db

import androidx.room.TypeConverter

open class StringListConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        return if (value.isNullOrEmpty()) {
            emptyList()
        } else {
            value.split("|").map { it.trim() }
        }
    }

    @TypeConverter
    fun toString(list: List<String>?): String {
        return list?.joinToString(separator = "|") ?: ""
    }
}
