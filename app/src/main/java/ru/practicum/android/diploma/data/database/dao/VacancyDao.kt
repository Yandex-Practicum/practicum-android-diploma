package ru.practicum.android.diploma.data.database.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.practicum.android.diploma.data.database.entity.AreaEntity
import ru.practicum.android.diploma.data.database.entity.EmployerEntity
import ru.practicum.android.diploma.data.database.entity.SalaryEntity
import ru.practicum.android.diploma.data.database.entity.VacancyEntity
import java.util.UUID

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavoriteVacancy(vacancy: VacancyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavoriteArea(area: AreaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavoriteEmployer(employer: EmployerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavoriteSalary(salary: SalaryEntity)

    @Query("SELECT * FROM area_table WHERE primary_key=:pk")
    suspend fun getFavoriteArea(pk: UUID): AreaEntity

    @Query("SELECT * FROM employer_table WHERE primary_key=:pk")
    suspend fun getFavoriteEmployer(pk: UUID): EmployerEntity

    @Query("SELECT * FROM salary_table WHERE primary_key=:pk")
    suspend fun getFavoriteSalary(pk: UUID): SalaryEntity

    @Transaction
    suspend fun setVacancyTransaction(vacancy: VacancyEntity,area: AreaEntity?,employer: EmployerEntity?, salary: SalaryEntity?){
        setFavoriteVacancy(vacancy = vacancy)
        if(area != null) {
            setFavoriteArea(area = area)
        }
        if(employer != null) {
            setFavoriteEmployer(employer = employer)
            Log.v("my", "dao1")
        }
        if(salary != null) {
            setFavoriteSalary(salary = salary)
            Log.v("my", "dao2")
        }
    }

    @Query("SELECT * FROM vacancy_table")
    suspend fun getAllFavoritesVacancies(): List<VacancyEntity>

    @Query("SELECT vacancy_id FROM vacancy_table")
    suspend fun getFavoritesListVacancyById(): List<Int>

    @Query("SELECT * FROM vacancy_table WHERE vacancy_id=:id")
    suspend fun getFavoritesVacancyById(id: Int): VacancyEntity

    @Query("SELECT EXISTS (SELECT 1 FROM vacancy_table WHERE vacancy_id =:id)")
    suspend fun checkInFavorite(id: Int): Boolean

    @Query("DELETE FROM vacancy_table WHERE vacancy_id =:id")
    suspend fun deleteVacancyFromFavorites(id: Int): Int?
}
