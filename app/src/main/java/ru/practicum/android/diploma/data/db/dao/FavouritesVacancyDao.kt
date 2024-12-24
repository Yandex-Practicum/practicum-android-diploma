package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entity.FavouritesVacancyEntity

@Dao
interface FavouritesVacancyDao {

    @Insert(FavouritesVacancyEntity::class, OnConflictStrategy.REPLACE)
    suspend fun insertFavouritesVacancyEntity(vacancy: FavouritesVacancyEntity)

    @Query("DELETE FROM favourites_vacancy_table WHERE vacancy_id = :id")
    suspend fun deleteFavouritesVacancyEntity(id: String)

    @Query("SELECT * FROM favourites_vacancy_table")
    suspend fun getFavouritesVacancyList(): List<FavouritesVacancyEntity>

    @Query("SELECT * FROM favourites_vacancy_table WHERE vacancy_id = :idOfSelectedVacancy")
    suspend fun getFavoriteVacancyById(idOfSelectedVacancy: String): FavouritesVacancyEntity?

}
