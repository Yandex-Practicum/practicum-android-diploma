package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entyti.VacancyDetailDtoEntity

@Dao
interface VacancyDtoDao {

    @Insert(entity = VacancyDetailDtoEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: VacancyDetailDtoEntity)

    @Query("DELETE FROM vacancy_table WHERE id = :vacancyId")
    suspend fun deleteVacancy(vacancyId: Int)

    @Query("SELECT * FROM vacancy_table")
    suspend fun getAllTrack(): List<VacancyDetailDtoEntity>
}
