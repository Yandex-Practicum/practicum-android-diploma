package ru.practicum.android.diploma.favorites.data.dao

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.data.db.entity.VacancyEntity

@Dao
interface FavoriteDao {
    @Insert(entity = VacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertVacancy(vacancy: VacancyEntity)

    @Query("DELETE FROM vacancy_favorites WHERE id = (:id)")
    fun deleteVacancyById(id: String)

    @Query("SELECT * FROM vacancy_favorites WHERE id = :id")
    fun getVacancyById(id: String): Flow<VacancyEntity>

    @Query("SELECT * FROM vacancy_favorites ORDER BY timeOfLikeSec DESC")
    @Throws(SQLiteException::class)
    fun getVacancyListByTime(): List<VacancyEntity>

    @Query("SELECT EXISTS(SELECT * FROM vacancy_favorites WHERE id = :vacancyId)")
    suspend fun isVacancyFavorite(vacancyId: String): Boolean
}
