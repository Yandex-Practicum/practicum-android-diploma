package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.model.VacancyEntity

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: VacancyEntity)

    @Query("DELETE FROM favorite_vacancies_table WHERE id = :vacancyId")
    suspend fun deleteVacancy(vacancyId: String)

    @Query("SELECT * FROM favorite_vacancies_table")
    fun getAllVacancies(): Flow<VacancyEntity>

    @Query("SELECT * FROM favorite_vacancies_table WHERE id = :vacancyId LIMIT 1")
    suspend fun getVacancyById(vacancyId: String): VacancyEntity?

    @Query("SELECT COUNT(*) FROM favorite_vacancies_table WHERE id = :vacancyId")
    suspend fun isVacancyFavorite(vacancyId: String): Int
}
