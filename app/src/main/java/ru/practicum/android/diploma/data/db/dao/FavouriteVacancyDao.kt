package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entyties.FavouriteVacancy

@Dao
interface FavouriteVacancyDao {
    @Insert(entity = FavouriteVacancy::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: FavouriteVacancy)

    @Delete(entity = FavouriteVacancy::class)
    suspend fun deleteVacancy(vacancy: FavouriteVacancy)

    @Query("SELECT * FROM favourites_vacancies")
    fun getFavouriteVacancies(): Flow<List<FavouriteVacancy>>
}
