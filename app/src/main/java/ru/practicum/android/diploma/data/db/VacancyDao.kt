package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query

@Dao
interface VacancyDao {

    @Delete
    suspend fun deleteVacancy(vacancy: VacancyEntity)

    @Query("SELECT * FROM favorite_vacancies WHERE id = :id")
    suspend fun getVacancyById(id: String): VacancyEntity?

    @Query("SELECT COUNT(*) FROM favorite_vacancies WHERE id = :id")
    suspend fun isFavorite(id: String): Int

    @Query("DELETE FROM favorite_vacancies WHERE id = :id")
    suspend fun deleteById(id: String)
}
