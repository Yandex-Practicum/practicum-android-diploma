package ru.practicum.android.diploma.domain.favourite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.FavouriteJob

interface FavouriteRepository {
    suspend fun addToFavourites()
    suspend fun removeFromFavourites(id: String)
    suspend fun updateFavouriteJob()
    fun getJobById(id: String): Flow<String?>
    fun getFavourites(): Flow<List<FavouriteJob>>
    fun checkJobInFavourites(id: String): Flow<Boolean>
}
