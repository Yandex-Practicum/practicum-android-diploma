package ru.practicum.android.diploma.domain.models.main

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.Resource

interface SearchInteractor {
    suspend fun searchTrack(query: String): List<Vacancy>
}
