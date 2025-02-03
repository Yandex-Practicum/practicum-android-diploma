package ru.practicum.android.diploma.common.sharedprefs.models

data class Filter(
    val areaCountry: Country? = null,
    val areaCity: City? = null,
    val industrySP: IndustrySP? = null,
    val salary: Int? = null,
    val withSalary: Boolean? = false
)
