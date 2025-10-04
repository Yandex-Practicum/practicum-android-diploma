package ru.practicum.android.diploma.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.database.entity.VacancyEntity

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavoriteVacancy(vacancy: VacancyEntity)
    @Query("SELECT * FROM vacancy_table")
    suspend fun getAllFavoritesVacancies():List<VacancyEntity>
    @Query("SELECT vacancy_id FROM vacancy_table")
    suspend fun getFavoritesVacancyById():VacancyEntity
}
