package ru.practicum.android.diploma.favorites.vacancies.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.favorites.vacancies.data.db.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacancyDao {

    @Query("SELECT * FROM favorite_vacancies ORDER BY addedAt DESC")
    suspend fun getAllFavorites(): List<FavoriteVacancyEntity>

    @Query("SELECT * FROM favorite_vacancies WHERE id = :id LIMIT 1")
    suspend fun getFavoriteById(id: String): FavoriteVacancyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(entity: FavoriteVacancyEntity)

    @Delete
    suspend fun deleteFavorite(entity: FavoriteVacancyEntity)

    @Query("DELETE FROM favorite_vacancies WHERE id = :id")
    suspend fun deleteFavoriteById(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_vacancies WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean
}
