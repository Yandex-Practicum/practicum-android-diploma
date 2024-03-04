package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entyti.VacancyDetailEntity

@Dao
interface VacancyDtoDao {

    @Insert(entity = VacancyDetailEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: VacancyDetailEntity)

    @Query("DELETE FROM vacancy_table WHERE id = :vacancyId")
    suspend fun deleteVacancy(vacancyId: Int)

    @Query("SELECT * FROM vacancy_table")
    suspend fun getAllVacancy(): List<VacancyDetailEntity>

    @Query("SELECT * FROM vacancy_table WHERE id=:id")
    suspend fun getVacancyId(id: String): VacancyDetailEntity

    @Query("SELECT id FROM vacancy_table")
    suspend fun getAllIdVacancy(): List<String>
}
