package ru.practicum.android.diploma.favorites.vacancies.data.db.converters

import androidx.room.TypeConverter

class FavoriteVacancyConverters {
    @TypeConverter
    fun fromStringListToDb(list: List<String>?): String? {
        return list?.joinToString(separator = DELIMITER)
    }

    @TypeConverter
    fun toStringList(raw: String?): List<String> {
        if (raw.isNullOrBlank()) return emptyList()
        return raw.split(DELIMITER).mapNotNull { item ->
            val value = item.trim()
            if (value.isEmpty()) null else value
        }
    }

    private companion object {
        const val DELIMITER = "||"
    }
}
