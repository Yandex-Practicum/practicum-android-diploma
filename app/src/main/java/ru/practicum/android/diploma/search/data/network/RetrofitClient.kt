package ru.practicum.android.diploma.search.data.network

import android.util.Log
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val hhApiService: HhApiService,
    private val internetController: InternetController
) : NetworkClient {
    override suspend fun doRequest(any: Any): Response {
        val response = hhApiService.search(any as String)
        Log.e("TAG", "doRequest: ${response.results}", )
        return Response()
    }
}