package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow

interface CheckOnLikeRepository {
    suspend fun favouritesCheck(id: String): Flow<Boolean>
}
