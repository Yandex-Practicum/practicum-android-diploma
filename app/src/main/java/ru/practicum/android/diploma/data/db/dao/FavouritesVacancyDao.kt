package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.FavouritesVacancyEntity

@Dao
interface FavouritesVacancyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouritesVacancyEntity(vacancy: FavouritesVacancyEntity)

    @Query("SELECT * FROM favourites_vacancy_table")
    fun getFavouritesVacancyList(): Flow<List<FavouritesVacancyEntity>>

    @Query("DELETE FROM favourites_vacancy_table WHERE vacancy_id = :id")
    suspend fun deleteFavouritesVacancyEntity(id: String)

    @Query("SELECT COUNT(*) > 0 FROM favourites_vacancy_table WHERE vacancy_id = :vacancyId")
    suspend fun isFavorite(vacancyId: String): Boolean
}
