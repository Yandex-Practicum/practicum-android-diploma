package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.data.search.network.Response

interface IndustriesRepository {
    suspend fun get(): Response
}
