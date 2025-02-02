package ru.practicum.android.diploma.common.sharedprefs.interactor

import ru.practicum.android.diploma.common.sharedprefs.models.City
import ru.practicum.android.diploma.common.sharedprefs.models.Country
import ru.practicum.android.diploma.common.sharedprefs.models.Filter
import ru.practicum.android.diploma.common.sharedprefs.models.Industry

interface SharedPrefsInteractor {
    fun saveFilter(filter: Filter)
    fun getFilter(): Filter
    fun updateCountry(country: Country)
    fun updateCity(city: City)
    fun updateIndustry(industry: Industry)
    fun updateSalary(salary: Int)
    fun updateWithSalary(withSalary: Boolean)
    fun clearCountry()
    fun clearCity()
    fun clearIndustry()
    fun clearSalary()
    fun clearWithSalary()
    fun clearAll()
}
