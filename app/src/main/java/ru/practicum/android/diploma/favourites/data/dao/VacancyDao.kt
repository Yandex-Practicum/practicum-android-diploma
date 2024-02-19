package ru.practicum.android.diploma.favourites.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.favourites.data.entity.FavoriteEntity

@Dao
interface VacancyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVacancyToFavorites(vacancy: DetailVacancy)

    @Delete
    suspend fun removeVacancyFromFavorites(vacancy: DetailVacancy)

    @Query("SELECT * FROM vacancy_favorites_table ORDER BY insertionTime DESC")
    fun getTrack(): Flow<List<FavoriteEntity>>

    @Query("SELECT id FROM vacancy_favorites_table")
    suspend fun getVacancyId(): List<String>
}
