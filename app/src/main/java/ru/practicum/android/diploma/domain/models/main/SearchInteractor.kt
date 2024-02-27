package ru.practicum.android.diploma.domain.models.main

import kotlinx.coroutines.flow.Flow

interface SearchInteractor {
    suspend fun searchTrack(queryMap: Map<String, String>): Flow<Pair<List<Vacancy>?, Int?>>
}
