package ru.practicum.android.diploma.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.database.entities.VacancyDetailsEntity

@Dao
interface VacancyDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVacancy(vacancy: VacancyDetailsEntity)

    @Delete
    fun deleteVacancy(vacancy: VacancyDetailsEntity)

    @Query("DELETE FROM $TABLE_NAME where id = :id")
    fun deleteVacancyById(id: String)

    @Query("SELECT * FROM $TABLE_NAME")
    fun getVacancies(): Flow<List<VacancyDetailsEntity>>

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    fun getVacancyByID(id: String): Flow<VacancyDetailsEntity>

    companion object {
        const val TABLE_NAME = "vacancy_details"
    }
}
