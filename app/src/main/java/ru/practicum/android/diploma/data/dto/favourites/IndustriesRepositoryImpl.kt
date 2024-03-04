package ru.practicum.android.diploma.data.dto.favourites

import ru.practicum.android.diploma.data.search.network.IndustriesRequest
import ru.practicum.android.diploma.data.search.network.NetworkClient
import ru.practicum.android.diploma.data.search.network.Response
import ru.practicum.android.diploma.domain.api.IndustriesRepository

class IndustriesRepositoryImpl(private val networkClient: NetworkClient): IndustriesRepository {
    override suspend fun get(): Response {
        return networkClient.doRequest(IndustriesRequest())
    }
}
