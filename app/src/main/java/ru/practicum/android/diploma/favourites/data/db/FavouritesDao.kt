package ru.practicum.android.diploma.favourites.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(vacancyEntity: VacancyEntity)

    @Query("SELECT * FROM vacancies_table ORDER BY timestamp DESC")
    fun getAll(): Flow<List<VacancyEntity>>

    @Query("SELECT * FROM vacancies_table WHERE id = :id")
    suspend fun getById(id: Int): VacancyEntity?

    @Query("SELECT id FROM vacancies_table")
    fun getIds(): Flow<List<Int>>

    @Query("DELETE FROM vacancies_table WHERE id = :id")
    suspend fun delete(id: Int)

}
