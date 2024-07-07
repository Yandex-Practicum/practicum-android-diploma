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

    @Query("SELECT * FROM vacancies_table ORDER BY id DESC")
    fun getAll(): Flow<List<VacancyEntity>>

    @Query("SELECT * FROM vacancies_table WHERE vacancyId = :vacancyId")
    suspend fun getById(vacancyId: Int): VacancyEntity?

    @Query("SELECT vacancyId FROM vacancies_table")
    fun getIds(): Flow<List<Int>>

    @Query("DELETE FROM vacancies_table WHERE vacancyId = :vacancyId")
    suspend fun delete(vacancyId: Int)

}
