package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.FavouriteVacancyDao
import ru.practicum.android.diploma.data.db.entyties.FavouriteVacancy

@Database(
    version = 1,
    entities = [FavouriteVacancy::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteVacancyDao(): FavouriteVacancyDao
}
