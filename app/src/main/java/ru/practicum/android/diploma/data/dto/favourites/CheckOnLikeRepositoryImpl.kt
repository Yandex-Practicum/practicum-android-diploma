package ru.practicum.android.diploma.data.dto.favourites

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.room.AppDatabase
import ru.practicum.android.diploma.domain.api.CheckOnLikeRepository

class CheckOnLikeRepositoryImpl(val db: AppDatabase) : CheckOnLikeRepository {
    override suspend fun favouritesCheck(id: String): Flow<Boolean> = flow {
        emit(db.vacancyDao().queryTrackId(id) != null)
    }
}
