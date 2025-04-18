package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IFavVacanciesDao {
    @Insert(entity = FavVacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: FavVacancyEntity)

    @Delete(entity = FavVacancyEntity::class)
    suspend fun deleteVacancy(vacancy: FavVacancyEntity)

    @Query("SELECT * FROM $FAV_VACANCIES_TABLE_NAME ")
    suspend fun getAllVacancies(): List<FavVacancyEntity>
}
