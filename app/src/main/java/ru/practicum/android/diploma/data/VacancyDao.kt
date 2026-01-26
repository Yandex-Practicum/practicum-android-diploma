package ru.practicum.android.diploma.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import kotlinx.coroutines.flow.Flow

@Dao
interface VacancyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: VacancyEntity)

    @Delete
    suspend fun deleteVacancy(vacancy: VacancyEntity)

    @Query("SELECT * FROM favorite_vacancies ORDER BY created_at DESC")
    fun getAllFavorites(): Flow<List<VacancyEntity>>

    @Query("SELECT * FROM favorite_vacancies WHERE id = :id")
    suspend fun getVacancyById(id: String): VacancyEntity?

    @Query("SELECT COUNT(*) FROM favorite_vacancies WHERE id = :id")
    suspend fun isFavorite(id: String): Int

    @Query("DELETE FROM favorite_vacancies WHERE id = :id")
    suspend fun deleteById(id: String)

   // @Query("SELECT * FROM favorite_vacancies WHERE industry_id = :industryId")
  //  suspend fun getByIndustry(industryId: String): List<VacancyEntity>
}
