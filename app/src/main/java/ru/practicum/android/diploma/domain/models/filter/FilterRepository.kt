package ru.practicum.android.diploma.domain.models.filter

interface FilterRepository {
    fun setSalary(input: String)
    fun getSalary(): String

    fun setSelectedCountry(country: Country?)
    fun getSelectedCountry(): Country?

    fun setSelectedArea(area: Area?)
    fun getSelectedArea(): Area?
}
