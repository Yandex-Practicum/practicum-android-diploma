package ru.practicum.android.diploma.details.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.data.model.VacancyEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vacancyEntity: VacancyEntity): Long
    @Query("DELETE FROM favorite_vacancies WHERE id =:id")
    suspend fun delete(id: Long): Int
    @Query("SELECT * FROM favorite_vacancies ORDER by date DESC")
    fun getFavorites(): Flow<List<VacancyEntity>>

}