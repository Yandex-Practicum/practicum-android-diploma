package ru.practicum.android.diploma.search.domain.utils

data class Options(
    val searchText: String,
    val itemsPerPage: Int,
    val page: Int,
) {
    companion object {
        fun toMap(options: Options): Map<String, String> = with(options) {
            mapOf(
                "text" to searchText,
                "per_page" to itemsPerPage.toString(),
                "page" to page.toString(),
            )
        }
    }
}
