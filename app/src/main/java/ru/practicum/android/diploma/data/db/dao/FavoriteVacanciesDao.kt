package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacanciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVacancy(vacancy: FavoriteVacancyEntity)

    @Query("SELECT * FROM favorite_vacancies_table")
    fun getVacancies(): Flow<List<FavoriteVacancyEntity>>

    @Delete(entity = FavoriteVacancyEntity::class)
    suspend fun deleteVacancy(vacancy: FavoriteVacancyEntity)
}
