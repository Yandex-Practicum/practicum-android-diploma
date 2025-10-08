package ru.practicum.android.diploma.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.database.entity.VacancyDetailEntity

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setFavoriteVacancy(vacancy: VacancyDetailEntity): Long

    @Query("SELECT * FROM vacancy_table WHERE vacancy_id=:id")
    suspend fun getFavoritesVacancyById(id: String): VacancyDetailEntity

    @Query("SELECT * FROM vacancy_table")
    suspend fun getAllFavoritesVacancies(): List<VacancyDetailEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM vacancy_table WHERE vacancy_id =:id)")
    suspend fun checkInFavorite(id: String): Boolean

    @Query("DELETE FROM vacancy_table WHERE vacancy_id =:id")
    suspend fun deleteVacancyFromFavorites(id: String): Int?
}
