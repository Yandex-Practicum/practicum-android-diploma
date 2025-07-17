package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.db.converter.TypeConverter
import ru.practicum.android.diploma.data.db.dao.FavouriteVacancyDao
import ru.practicum.android.diploma.data.db.entyties.FavouriteVacancy

@Database(
    version = 2,
    entities = [FavouriteVacancy::class]
)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteVacancyDao(): FavouriteVacancyDao
}
