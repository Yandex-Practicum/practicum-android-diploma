package ru.practicum.android.diploma.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.practicum.android.diploma.data.database.entity.AreaEntity
import ru.practicum.android.diploma.data.database.entity.EmployerEntity
import ru.practicum.android.diploma.data.database.entity.SalaryEntity
import ru.practicum.android.diploma.data.database.entity.VacancyEntity

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavoriteVacancy(vacancy: VacancyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavoriteArea(area: AreaEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavoriteEmployer(employer: EmployerEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavoriteSalary(salary: SalaryEntity?)

    @Transaction
    suspend fun setVacancyTransaction(vacancy: VacancyEntity,area: AreaEntity?,employer: EmployerEntity?, salary: SalaryEntity?){
        setFavoriteVacancy(vacancy = vacancy)
        setFavoriteArea(area = area)
        setFavoriteEmployer(employer = employer)
        setFavoriteSalary(salary = salary)
    }

    @Query("SELECT * FROM vacancy_table")
    suspend fun getAllFavoritesVacancies(): List<VacancyEntity>

    @Query("SELECT vacancy_id FROM vacancy_table")
    suspend fun getFavoritesVacancyById(): List<Int>

    @Query("SELECT EXISTS (SELECT 1 FROM vacancy_table WHERE vacancy_id =:id)")
    suspend fun checkInFavorite(id: Int): Boolean

    @Query("DELETE FROM vacancy_table WHERE vacancy_id =:id")
    suspend fun deleteVacancyFromFavorites(id: Int): Int?
}
