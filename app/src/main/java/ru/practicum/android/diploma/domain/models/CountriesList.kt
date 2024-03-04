package ru.practicum.android.diploma.domain.models

class CountrySortOrder {

    val customOrder = listOf(
        "Россия",
        "Украина",
        "Казахстан",
        "Азербайджан",
        "Беларусь",
        "Грузия",
        "Кыргызстан",
        "Узбекистан",
        "Другие регионы"
    )

    companion object {
        fun sortCountriesListManually(countries: List<Country>): List<Country> {
            val sortOrder = CountrySortOrder()
            return countries.sortedBy { sortOrder.customOrder.indexOf(it.name) }
        }
    }
}
