package ru.practicum.android.diploma.domain.models.main

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.Resource

interface SearchInteractor {
    suspend fun searchTrack(queryMap: Map<String, String>): Flow<Pair<List<Vacancy>?, Int?>>
}
