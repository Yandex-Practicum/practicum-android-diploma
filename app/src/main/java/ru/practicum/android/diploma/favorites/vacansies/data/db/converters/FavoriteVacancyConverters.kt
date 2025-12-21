package ru.practicum.android.diploma.favorites.vacansies.data.db.converters

import androidx.room.TypeConverter

// поле skills с сервера приходит в виде List<String>, Room не может хранить списки
// вместо создания отдельной таблицы для skills сделал конвертеры
class FavoriteVacancyConverters {
    @TypeConverter
    fun fromSkillsToDb(skills: List<String>?): String? {
        return skills?.joinToString(separator = SKILLS_DELIMITER)
    }

    @TypeConverter
    fun toSkills(raw: String?): List<String> {
        if (raw.isNullOrBlank()) return emptyList()
        return raw.split(SKILLS_DELIMITER).mapNotNull { item ->
            val value = item.trim()
            if (value.isEmpty()) null else value
        }
    }

    private companion object {
        const val SKILLS_DELIMITER = "||"
    }
}
