package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.dao.models.VacancyEntity

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addVacancy(vacancyEntity: VacancyEntity)

    @Query("SELECT * FROM table_favorite_vacancies WHERE id=:vacancyId")
    suspend fun getVacancyDetailsById(vacancyId: String): VacancyEntity

    @Query("SELECT * FROM table_favorite_vacancies LIMIT :limit OFFSET :from")
    suspend fun getFavoritesPage(limit: Int, from: Int): List<VacancyEntity>

    @Query("SELECT * FROM table_favorite_vacancies")
    suspend fun getAllFavorites(): List<VacancyEntity>

    @Query("SELECT COUNT(*) FROM table_favorite_vacancies WHERE id=:vacancyId")
    suspend fun isVacancyFavorite(vacancyId: String): Int

    @Query("SELECT COUNT(*) FROM table_favorite_vacancies")
    suspend fun favoriteCount(): Int

    @Delete
    suspend fun removeVacancy(vacancyEntity: VacancyEntity)

    @Query("DELETE FROM table_favorite_vacancies WHERE id=:vacancyId")
    suspend fun removeVacancyById(vacancyId: String)
}
