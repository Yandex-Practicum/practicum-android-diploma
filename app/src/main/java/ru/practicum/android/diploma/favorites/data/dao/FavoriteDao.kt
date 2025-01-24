package ru.practicum.android.diploma.favorites.data.dao

import androidx.room.Query
import ru.practicum.android.diploma.common.data.db.entity.VacancyEntity

interface FavoriteDao {
    // метод @Query для получения списка со всеми треками, добавленными в избранное;
    @Query("SELECT * FROM vacancy_entity ORDER BY timeOfLikeSec DESC")
    fun getVacancyListByTime(): List<VacancyEntity>
}
