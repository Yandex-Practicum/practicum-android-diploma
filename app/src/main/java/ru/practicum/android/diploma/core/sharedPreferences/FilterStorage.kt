package ru.practicum.android.diploma.core.sharedPreferences

import ru.practicum.android.diploma.filter.domain.models.Filter

interface FilterStorage {
    fun saveCountry(country: String)
    fun saveRegion(region: String)
    fun saveIndustry(industry: String)
    fun saveSalary(salary: Int)
    fun saveShowWithSalary(isChecked: Boolean)
    fun getCountry(): String
    fun getRegion(): String
    fun getIndustry(): String
    fun getSalary(): Int
    fun getShowWithSalary(): Boolean
    fun deleteCountry()
    fun deleteRegion()
    fun deleteIndustry()
    fun deleteSalary()
    fun deleteShowWithSalary()
    fun getFilter(): Filter
    fun clearFilter()
}
