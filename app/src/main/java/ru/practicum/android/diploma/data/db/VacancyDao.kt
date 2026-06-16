package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface VacancyDao {
    @Insert(entity = VacancyEntity::class, onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertVacancy(vacancy: VacancyEntity)

    @Query("SELECT * FROM fav_vacancies_table ORDER BY id DESC")
    suspend fun getFavoriteVacancies(): List<VacancyEntity>

    @Query("DELETE FROM fav_vacancies_table WHERE id = :vacancyId")
    suspend fun deleteVacancy(vacancyId: Int)

    @Query("SELECT id FROM fav_vacancies_table WHERE id = :vacancyId")
    suspend fun getVacancyId(vacancyId: Int): Int

    @Transaction
    suspend fun deleteAndInsertVacancy(vacancy: VacancyEntity) {
        deleteVacancy(vacancy.id)
        insertVacancy(vacancy)
    }
}
