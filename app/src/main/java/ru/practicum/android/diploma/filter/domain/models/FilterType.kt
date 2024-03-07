package ru.practicum.android.diploma.filter.domain.models

sealed interface FilterType {
    data class Country(val id: Int, val name: String) : FilterType
    data class Region(val id: Int, val name: String) : FilterType
    data class Industry(val id: Int, val name: String) : FilterType
    data class Salary(val amount: Int) : FilterType
    data class ShowWithSalaryFlag(val flag: Boolean) : FilterType
}
