package ru.practicum.android.diploma.search.data.network

import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val hhApiService: HhApiService,
    private val internetController: InternetController
) : NetworkClient {
    override fun doRequest(any: Any): Response {
        TODO("Not yet implemented")
    }
}