package ru.practicum.android.diploma.domain.search.models

class SearchParams(
    val searchQuery: String,
    var nameOfCityForFilter: String? = null,
    var nameOfIndustryForFilter: String? = null,
    var onlyWithSalary: Boolean = false,
    var currencyOfSalary: String? = null,
    var expectedSalary: String? = null,
    val numberOfVacanciesOnPage: String = "20",
    var numberOfPage: String
)
