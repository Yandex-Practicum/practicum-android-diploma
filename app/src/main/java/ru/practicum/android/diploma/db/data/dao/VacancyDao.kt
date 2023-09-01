package ru.practicum.android.diploma.db.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.db.data.entity.VacancyEntity

@Dao
interface VacancyDao {
    @Insert(entity = VacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancyEntity: VacancyEntity)

    @Delete(entity = VacancyEntity::class)
    suspend fun deleteVacancy(vacancyEntity: VacancyEntity)

    @Query("SELECT * FROM vacancy_table")
    fun getFavouriteVacancy(): Flow<List<VacancyEntity>>

    @Query("SELECT * FROM vacancy_table WHERE id = :vacancyId")
    fun getFavouriteVacancyById(vacancyId: Int): Flow<VacancyEntity>
}