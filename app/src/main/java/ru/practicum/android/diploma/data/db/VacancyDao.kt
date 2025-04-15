package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VacancyDao {
    @Insert(entity = VacancyShortDbEntity::class,onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: VacancyShortDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vacancies: List<VacancyShortDbEntity>)

    @Query("SELECT * FROM Favorite_vacancies_table")
    fun getAllFlow(): Flow<List<VacancyShortDbEntity>>

    @Query("SELECT * FROM Favorite_vacancies_table")
    suspend fun getAll(): List<VacancyShortDbEntity>

    @Query("SELECT * FROM Favorite_vacancies_table WHERE vacancy_id = :id")
    suspend fun getById(id: Int): VacancyShortDbEntity?

    @Delete (entity = VacancyShortDbEntity::class)
    suspend fun delete(vacancy: VacancyShortDbEntity)

    @Query("DELETE FROM Favorite_vacancies_table")
    suspend fun clear()

    @Query("DELETE FROM Favorite_vacancies_table WHERE vacancy_id = :id")
    suspend fun deleteById(id: Int)
}
