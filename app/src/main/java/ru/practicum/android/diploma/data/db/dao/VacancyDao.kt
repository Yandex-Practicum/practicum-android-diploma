package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.VacancyEntity

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVacancy(entity: VacancyEntity)

    @Query("DELETE FROM favourites_table WHERE id = :vacancyId")
    suspend fun deleteVacancy(vacancyId: String)

    @Query("SELECT * FROM favourites_table")
    fun getVacancyList(): Flow<List<VacancyEntity>>

    @Update
    suspend fun updateVacancy(entity: VacancyEntity)
}
