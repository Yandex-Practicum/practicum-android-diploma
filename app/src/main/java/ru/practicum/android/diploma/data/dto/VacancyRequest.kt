package ru.practicum.android.diploma.data.dto

class VacancyRequest(
    private val text: String,
    private val page: Int?
) {
    fun map(): HashMap<String, String> {
        val options: HashMap<String, String> = HashMap()
        options["text"] = text
        if (page != null) {
            options["page"] = page.toString()
        }
        return options
    }
}
