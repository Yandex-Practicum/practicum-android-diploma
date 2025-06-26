package ru.practicum.android.diploma.data.db.converters

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? =
        value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? =
        date?.time
}
