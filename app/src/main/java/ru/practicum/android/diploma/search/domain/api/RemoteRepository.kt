package ru.practicum.android.diploma.search.domain.api

interface RemoteRepository {
    suspend fun search(query: String)
}