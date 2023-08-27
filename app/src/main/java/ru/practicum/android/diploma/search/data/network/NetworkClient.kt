package ru.practicum.android.diploma.search.data.network

interface NetworkClient {
    fun doRequest(any: Any): Response
}