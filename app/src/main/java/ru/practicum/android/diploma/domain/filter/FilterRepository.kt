package ru.practicum.android.diploma.domain.filter

import ru.practicum.android.diploma.domain.models.filter.Area
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.domain.models.filter.Industry

interface FilterRepository {
    fun setSalary(input: String)
    fun getSalary(): String
    fun setSelectedCountry(country: Country?)
    fun getSelectedCountry(): Country?
    fun setSelectedArea(area: Area?)
    fun getSelectedArea(): Area?
    fun getSelectedIndustries(): List<Industry>?
    fun setSelectedIndustries(industry: List<Industry>?)
    fun clear()
    fun getCheckedStatus(): Boolean
    fun setCheckedStatus(isChecked: Boolean)
}