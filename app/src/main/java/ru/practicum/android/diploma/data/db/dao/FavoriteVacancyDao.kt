package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacancyDao {
    @Insert(entity = FavoriteVacancyEntity::class, OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteVacancy(vacancy: FavoriteVacancyEntity)

    @Delete(entity = FavoriteVacancyEntity::class)
    suspend fun deleteFavoriteVacancy(vacancy: FavoriteVacancyEntity): Int
}
