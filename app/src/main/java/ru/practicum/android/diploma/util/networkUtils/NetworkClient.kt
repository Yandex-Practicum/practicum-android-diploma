package ru.practicum.android.diploma.util.networkUtils

interface NetworkClient {
   suspend fun doRequest(dto: Any): Response
}
