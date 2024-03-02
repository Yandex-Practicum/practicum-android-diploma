package ru.practicum.android.diploma.domain.models

enum class CountriesList(val countryName: String) {
    RUSSIA("Россия"),
    UKRAINE("Украина"),
    KAZAKHSTAN("Казахстан"),
    AZERBAIJAN("Азербайджан"),
    BELARUS("Беларусь"),
    GEORGIA("Грузия"),
    KYRGYZSTAN("Кыргызстан"),
    UZBEKISTAN("Узбекистан");

    companion object {
        fun containsCountry(countryName: String): Boolean {
            return values().any { it.countryName == countryName }
        }
    }
}
