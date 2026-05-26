package ru.practicum.android.diploma.core.data.network

sealed class Request {
    data class SearchRequest(
        val params: Map<String, String>
    ) : Request()

    object IndustriesRequest : Request()
}
