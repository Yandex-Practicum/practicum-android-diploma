package ru.practicum.android.diploma.details.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.data.local.model.VacancyFullInfoEntity

@Dao
interface FavoriteDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vacancyEntity: VacancyFullInfoEntity)
    
    @Query("DELETE FROM favorite_vacancies_full_info WHERE id =:id")
    suspend fun delete(id: String): Int
    
    @Query("SELECT * FROM favorite_vacancies_full_info ORDER by date DESC")
    fun getFavorites(): Flow<List<VacancyFullInfoEntity>>

    @Query("SELECT * FROM favorite_vacancies_full_info WHERE id =:id")
    fun getFavoritesById(id: String): Flow<VacancyFullInfoEntity>
}