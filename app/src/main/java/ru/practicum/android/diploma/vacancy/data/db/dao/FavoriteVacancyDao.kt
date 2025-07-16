package ru.practicum.android.diploma.vacancy.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.data.db.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(vacancy: FavoriteVacancyEntity)

    @Delete
    suspend fun deleteFavorite(vacancy: FavoriteVacancyEntity)

    @Query("SELECT * FROM favorite_vacancy_table")
    fun getAllFavorites(): Flow<List<FavoriteVacancyEntity>>

    @Query("SELECT * FROM favorite_vacancy_table WHERE id = :vacancyId")
    suspend fun getFavoriteById(vacancyId: String): FavoriteVacancyEntity?

    @Query("DELETE FROM favorite_vacancy_table WHERE id = :vacancyId")
    suspend fun deleteFavoriteById(vacancyId: String)
}
