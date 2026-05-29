package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVacancy(vacancy: FavoriteVacancyEntity)

    @Query("DELETE FROM favorite_vacancies_table WHERE id = :id")
    suspend fun deleteVacancy(id: String): Int

    @Query("SELECT * FROM favorite_vacancies_table")
    fun observeFavoriteVacancies(): Flow<List<FavoriteVacancyEntity>>

    @Query("SELECT * FROM favorite_vacancies_table WHERE id = :id")
    fun observeFavoriteVacancy(id: String): Flow<FavoriteVacancyEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_vacancies_table WHERE id = :id)")
    suspend fun checkVacancyIsFavorite(id: String): Boolean
}
