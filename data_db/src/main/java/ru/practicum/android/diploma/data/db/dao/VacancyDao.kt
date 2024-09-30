package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entity.VacancyEntity

@Dao
interface VacancyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateVacancy(vacancy: VacancyEntity): Long

    @Query("DELETE FROM VACANCY_TABLE WHERE ID_VACANCY = :id")
    suspend fun deleteVacancy(id: Int): Int

    @Query("SELECT ID_VACANCY FROM VACANCY_TABLE")
    suspend fun getVacancyIds(): List<Int>

    @Query("SELECT * FROM VACANCY_TABLE ORDER BY DATE_ADD_VACANCY DESC")
    suspend fun getVacancies(): List<VacancyEntity>

    @Query("DELETE FROM VACANCY_TABLE")
    suspend fun deleteVacancies()

}
