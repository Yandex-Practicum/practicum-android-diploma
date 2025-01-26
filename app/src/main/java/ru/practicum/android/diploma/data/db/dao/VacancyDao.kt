package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.VacancyEntity

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorites(vacancy: VacancyEntity)

    @Delete
    suspend fun removeFromFavorites(vacancy: VacancyEntity)

    @Query("DELETE FROM favorite_table WHERE vacancyId = :vacancyId")
    suspend fun removeById(vacancyId: Long)

    @Query("SELECT * FROM favorite_table ORDER BY timeStamp DESC")
    fun getAllFavorites(): Flow<List<VacancyEntity>>

    @Query("SELECT vacancyId FROM favorite_table WHERE vacancyId = :vacancyId")
    fun getFavoritesById(vacancyId: Long): Flow<List<VacancyEntity>>
}
