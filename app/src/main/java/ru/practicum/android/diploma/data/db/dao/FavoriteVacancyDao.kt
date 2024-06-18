package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacancyDao {
    @Insert(entity = FavoriteVacancyEntity::class, OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteVacancy(vacancy: FavoriteVacancyEntity)

    @Delete(entity = FavoriteVacancyEntity::class)
    suspend fun deleteFavoriteVacancy(vacancy: FavoriteVacancyEntity): Int

    @Query("SELECT * FROM favorite_vacancy_table")
    suspend fun getFavorites(): List<FavoriteVacancyEntity>

    @Query("SELECT * FROM favorite_vacancy_table WHERE vacancyId = :vacancyId")
    suspend fun getVacancyById(vacancyId: String): FavoriteVacancyEntity

    @Query("SELECT vacancyId FROM favorite_vacancy_table")
    suspend fun getFavoriteIds(): List<String>
}
