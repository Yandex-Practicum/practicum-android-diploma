package ru.practicum.android.diploma.data.dto.network

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.domain.NetworkClient

class RetrofitNetworkClient(
    private val hhService: HhApi,
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        TODO("Not yet implemented")
    }

}
