package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entity.VacancyEntity

@Dao
interface VacancyDao {
    @Insert(entity = VacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVacancy(vacancyEntity: VacancyEntity)

    @Query("SELECT id FROM vacancy_table")
    suspend fun getVacanciesId(): List<String>

    @Query("SELECT * FROM vacancy_table")
    suspend fun getFavoriteVacancies(): List<VacancyEntity>

}