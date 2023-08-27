package ru.practicum.android.diploma.search.domain

interface RemoteRepository {
    suspend fun search(query: String)
}