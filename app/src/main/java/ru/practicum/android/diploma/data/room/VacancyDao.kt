package ru.practicum.android.diploma.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.favourites.room.VacancyEntity

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVacancy(entity: VacancyEntity)

    @Query("DELETE FROM favourites_table WHERE id = :vacancyId")
    suspend fun deleteVacancy(vacancyId: String)

    @Query("SELECT * FROM favourites_table")
    fun getVacancyList(): Flow<List<VacancyEntity>>

    @Query("SELECT * FROM favourites_table WHERE id = :vacancyId LIMIT 1")
    suspend fun getVacancyById(vacancyId: String): VacancyEntity?

    @Query("SELECT * FROM favourites_table WHERE id=:searchId")
    fun queryTrackId(searchId: String): VacancyEntity?

    @Update
    suspend fun updateVacancy(entity: VacancyEntity)
}
