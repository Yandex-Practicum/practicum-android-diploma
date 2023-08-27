package ru.practicum.android.diploma.search.data

import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val networkClient: NetworkClient): RemoteRepository {
    override suspend fun search(query: String) {
        networkClient.doRequest(query)
    }
}