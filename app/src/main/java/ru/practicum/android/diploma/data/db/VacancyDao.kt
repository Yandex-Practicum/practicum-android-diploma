package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.practicum.android.diploma.data.db.entity.VacancyEntity

@Dao
interface VacancyDao {
    @Insert(entity = VacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVacancy(vacancyEntity: VacancyEntity)

    @Query("SELECT id FROM vacancy_table")
    suspend fun getVacanciesId(): List<String>

    @Query("SELECT * FROM vacancy_table")
    suspend fun getFavoriteVacancies(): List<VacancyEntity>
    @Delete
    suspend fun delete(vacancyEntity: VacancyEntity)

    @Query("SELECT * FROM vacancy_table WHERE id = :id")
    suspend fun getFavoriteVacancy(id: String): VacancyEntity?
    @Update
    suspend fun updateVacancy(vacancyEntity: VacancyEntity)

}