package ru.practicum.android.diploma.favorites.data.dao

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.common.data.db.entity.VacancyEntity

@Dao
interface FavoriteDao {
    @Insert(entity = VacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertVacancy(vacancy: VacancyEntity)

    @Delete(entity = VacancyEntity::class)
    fun deleteVacancy(vacancy: VacancyEntity)

    @Query("DELETE FROM vacancy_entity WHERE id = (:id)")
    fun deleteVacancyById(id: Int)

    @Query("SELECT * FROM vacancy_entity ORDER BY timeOfLikeSec DESC")
    @Throws(SQLiteException::class)
    fun getVacancyListByTime(): List<VacancyEntity>
}
