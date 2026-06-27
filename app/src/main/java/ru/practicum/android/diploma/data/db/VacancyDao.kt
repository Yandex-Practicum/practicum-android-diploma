package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.VacancyEntity

@Dao
interface VacancyDao {
    @Insert(entity = VacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: VacancyEntity)

    @Query("SELECT * FROM fav_vacancies_table ORDER BY vacancy_id DESC")
    fun getFavoriteVacancies(): Flow<List<VacancyEntity>>

    @Query("DELETE FROM fav_vacancies_table WHERE vacancy_id = :vacancyId")
    suspend fun deleteVacancy(vacancyId: String)

    @Query("SELECT vacancy_id FROM fav_vacancies_table WHERE vacancy_id = :vacancyId")
    suspend fun getVacancyId(vacancyId: String): String?

    @Query("SELECT * FROM fav_vacancies_table WHERE vacancy_id = :vacancyId")
    suspend fun getVacancyById(vacancyId: String): VacancyEntity?
}
