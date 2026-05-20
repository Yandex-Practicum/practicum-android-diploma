package ru.practicum.android.diploma.search.data

import ru.practicum.android.diploma.core.data.network.NetworkClient

class SearchRepositoryImpl(private val networkClient: NetworkClient) {

    fun searchVacancies(query: String, page: Int, perPage: Int, filters: Map<String, String>) {

        val params = buildMap {
            put("text", query)
            put("page", page.toString())
            put("per_page", perPage.toString())

            filters.forEach { (key, value) ->
                if (value.isNotBlank()) {
                    put(key, value)
                }
            }
        }

//        val response = networkClient.doRequest()

    }
}
