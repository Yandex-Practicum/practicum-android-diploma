package ru.practicum.android.diploma.search.data.model

class VacancyRequest(
    val page: Int = 0,
    val perPage: Int = 20,
    val text: String,
    val area: String? = null
) {
    fun toQueryMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map["text"] = text

        area?.let {
            map["area"] = it
        }
        map["page"] = page.toString()
        map["per_page"] = perPage.toString()
        return map
    }
}
