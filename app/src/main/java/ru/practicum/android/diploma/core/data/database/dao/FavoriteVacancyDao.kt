package ru.practicum.android.diploma.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import ru.practicum.android.diploma.favorites.data.dto.FavoriteVacancyEntity

@Dao
interface FavoriteVacancyDao {
    @Query("SELECT * FROM favorite_vacancy ORDER BY id DESC")
    suspend fun getFavoriteVacancy() : List<FavoriteVacancyEntity>
}
