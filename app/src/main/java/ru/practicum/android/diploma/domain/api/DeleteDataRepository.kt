package ru.practicum.android.diploma.domain.api

interface DeleteDataRepository {
    suspend fun delete(data: String)
}
