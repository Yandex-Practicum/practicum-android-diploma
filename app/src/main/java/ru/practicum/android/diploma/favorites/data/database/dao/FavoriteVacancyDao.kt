package ru.practicum.android.diploma.favorites.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorites.data.dto.FavoriteContactEntity
import ru.practicum.android.diploma.favorites.data.dto.FavoriteKeySkillEntity
import ru.practicum.android.diploma.favorites.data.dto.FavoritePhoneEntity
import ru.practicum.android.diploma.favorites.data.dto.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorites.data.dto.FavoriteVacancyWithSkillsAndContactsEntity

@Dao
interface FavoriteVacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavoriteVacancyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhones(items: List<FavoritePhoneEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(item: FavoriteContactEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkills(items: List<FavoriteKeySkillEntity>)

    @Query("DELETE FROM favorite_vacancy WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM favorite_vacancy")
    fun getAll(): Flow<List<FavoriteVacancyEntity>>

    @Transaction
    @Query("SELECT * FROM favorite_vacancy WHERE id = :id LIMIT 1")
    fun getById(id: String): Flow<FavoriteVacancyWithSkillsAndContactsEntity?>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_vacancy WHERE id = :id)")
    fun isFavorite(id: String): Flow<Boolean>
}
