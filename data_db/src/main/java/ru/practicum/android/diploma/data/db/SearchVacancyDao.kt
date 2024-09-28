package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SearchVacancyDao {
    @Insert
    suspend fun insert(searchVacancy: SearchVacancy)

    @Query("SELECT * FROM vacancies_table")
    suspend fun getAllVacancies(): List<SearchVacancy>


    @Insert
    suspend fun insertAll(vacancies: List<SearchVacancy>)

    @Update
    suspend fun update(searchVacancy: SearchVacancy)

    @Delete
    suspend fun delete(searchVacancy: SearchVacancy)

    @Query("DELETE FROM vacancies_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM vacancies_table WHERE code = :vacancyCode")
    suspend fun getVacancyByCode(vacancyCode: String): SearchVacancy?

    @Query("SELECT * FROM vacancies_table WHERE areas LIKE '%' || :vacancyArea || '%'")
    suspend fun getVacanciesByArea(vacancyArea: String): List<SearchVacancy>

    @Query("SELECT * FROM vacancies_table WHERE industry LIKE '%' || :vacancyIndustry || '%'")
    suspend fun getVacanciesByIndustry(vacancyIndustry: String): List<SearchVacancy>

}
