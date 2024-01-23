package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entity.VacancyEntity

@Dao
interface FavoriteDao {
    @Insert(entity = VacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteVacancy(vacancyEntity: VacancyEntity)

    @Query("SELECT * FROM favorite_table ORDER BY date DESC;")
    suspend fun getFavoriteVacancy(): List<VacancyEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_table WHERE id LIKE :id)")
    suspend fun isFavorite(id: String): Boolean

    @Delete(entity = VacancyEntity::class)
    suspend fun deleteFavoriteVacancy(trackEntity: VacancyEntity)
}
