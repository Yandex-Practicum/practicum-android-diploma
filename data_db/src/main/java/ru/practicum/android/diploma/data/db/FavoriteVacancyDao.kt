package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteVacancyDao { //фильтров не экране избранного не вижу, так что минимум
    @Insert
    suspend fun insert(favoriteVacancy: FavoriteVacancy)

    @Delete
    suspend fun delete(favoriteVacancy: FavoriteVacancy)

    @Query("DELETE FROM favorite_vacancies_table")
    suspend fun deleteAll()

}
