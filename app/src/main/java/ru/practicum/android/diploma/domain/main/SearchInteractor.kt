package ru.practicum.android.diploma.domain.main

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.main.SearchingVacancies

interface SearchInteractor {
    suspend fun searchTrack(queryMap: Map<String, String>): Flow<Pair<SearchingVacancies?, Int?>>
}
