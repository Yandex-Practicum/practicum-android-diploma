package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entity.VacanciesEntity

@Dao
interface VacanciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavorite(favoriteVacancies: VacanciesEntity)

    @Delete
    suspend fun deleteFromFavorite(favoriteVacancies: VacanciesEntity)

    @Query("SELECT * FROM vacancies ORDER by dateAdd DESC")
    suspend fun getFavoritesVacancies(): List<VacanciesEntity>

    @Query("SELECT * FROM vacancies WHERE id=:vacId")
    suspend fun getFavoriteVacancieById(vacId: String): VacanciesEntity
}
