package ru.practicum.android.diploma.data.filter.local

import ru.practicum.android.diploma.domain.models.filter.Area
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.domain.models.filter.Industry

interface LocalStorage {

    fun setSalary(salary: String)
    fun getSalary(): String
    fun setSelectedCountry(country: Country?)
    fun getSelectedCountry(): Country?
    fun setSelectedArea(area: Area?)
    fun getSelectedArea(): Area?
    fun setSelectedIndustry(industry: Industry?)
    fun getSelectedIndustry(): Industry?
    fun clear()
    fun getCheckedStatus(): Boolean
    fun setCheckedStatus(isChecked: Boolean)
}
