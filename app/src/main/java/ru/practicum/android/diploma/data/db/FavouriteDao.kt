package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoritesEntity: FavouriteJob)

    @Query("DELETE FROM favourites WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM favourites ORDER BY insertion_timestamp")
    suspend fun getAll(): List<FavouriteJob>

    @Query("SELECT * FROM favourites WHERE id = :id")
    suspend fun getFavoriteVacancyById(id: String): FavouriteJob?

    @Query("SELECT EXISTS(SELECT 1 FROM favourites WHERE id = :id LIMIT 1)")
    suspend fun isFavorite(id: String): Boolean

    @Update(entity = FavouriteJob::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavoriteVacancy(favoritesEntity: FavouriteJob)

}
