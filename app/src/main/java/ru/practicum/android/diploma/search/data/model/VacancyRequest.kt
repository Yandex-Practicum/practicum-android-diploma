package ru.practicum.android.diploma.search.data.model

import android.util.Log

class VacancyRequest(
    val page: Int = 0,
    val perPage: Int = 20,
    val text: String,
    val filters: Map<String, String?> = emptyMap()
) {
    fun toQueryMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map["text"] = text
        map["page"] = page.toString()
        map["per_page"] = perPage.toString()
        filters.forEach { (key, value) ->
            value?.let { map[key] = it }
        }
        Log.d("VacancyRequest", "Итоговая карта для запроса: $map")
        return map
    }
}
