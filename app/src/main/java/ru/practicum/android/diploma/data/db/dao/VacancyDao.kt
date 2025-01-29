package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.ShortVacancyEntity
import ru.practicum.android.diploma.data.db.entity.VacancyEntity

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorites(vacancy: VacancyEntity)

    @Delete
    suspend fun removeFromFavorites(vacancy: VacancyEntity)

    @Query("DELETE FROM favorites_table WHERE vacancy_id = :vacancyId")
    suspend fun removeById(vacancyId: Long)

    @Query("SELECT * FROM favorites_table ORDER BY timeStamp DESC")
    fun getAllFavorites(): Flow<List<VacancyEntity>>

    @Query("SELECT * FROM favorites_table WHERE vacancy_id = :vacancyId")
    fun getFavoriteById(vacancyId: Long): VacancyEntity

    @Query("SELECT vacancy_id, name, employer, salary, address, timeStamp FROM favorites_table ORDER BY timeStamp DESC")
    fun getFavoritesList(): Flow<List<ShortVacancyEntity>>
}
