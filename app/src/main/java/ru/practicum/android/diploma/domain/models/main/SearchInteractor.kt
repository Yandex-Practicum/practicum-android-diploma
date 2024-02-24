package ru.practicum.android.diploma.domain.models.main

import kotlinx.coroutines.flow.Flow

interface SearchInteractor {
    fun searchTrack(term: String): Flow<List<Vacancy>>
}
