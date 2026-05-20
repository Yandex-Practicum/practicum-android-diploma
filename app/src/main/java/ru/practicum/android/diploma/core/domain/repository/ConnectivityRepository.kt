package ru.practicum.android.diploma.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface ConnectivityRepository {
    fun isConnected(): Boolean
    fun observe(): Flow<Boolean>
}
