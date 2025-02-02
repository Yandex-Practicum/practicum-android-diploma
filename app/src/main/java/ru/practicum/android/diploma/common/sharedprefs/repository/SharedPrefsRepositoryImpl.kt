package ru.practicum.android.diploma.common.sharedprefs.repository

import ru.practicum.android.diploma.common.sharedprefs.SharedPrefsUtil
import ru.practicum.android.diploma.common.sharedprefs.models.City
import ru.practicum.android.diploma.common.sharedprefs.models.Country
import ru.practicum.android.diploma.common.sharedprefs.models.Filter
import ru.practicum.android.diploma.common.sharedprefs.models.Industry

class SharedPrefsRepositoryImpl(private val sharedPrefsUtil: SharedPrefsUtil) : SharedPrefsRepository {

    override fun saveFilter(filter: Filter) {
        sharedPrefsUtil.saveFilter(filter)
    }

    override fun getFilter(): Filter {
        return sharedPrefsUtil.getFilter()
    }

    override fun updateField(country: Country?, city: City?, industry: Industry?, salary: Int?, withSalary: Boolean?) {
        val currentFilter = getFilter()
        val updatedFilter = currentFilter.copy(
            areaCountry = country ?: currentFilter.areaCountry,
            areaCity = city ?: currentFilter.areaCity,
            industry = industry ?: currentFilter.industry,
            salary = salary ?: currentFilter.salary,
            withSalary = withSalary ?: currentFilter.withSalary
        )
        saveFilter(updatedFilter)
    }

    override fun clearField(country: Boolean, city: Boolean, industry: Boolean, salary: Boolean, withSalary: Boolean) {
        val currentFilter = getFilter()
        val updatedFilter = currentFilter.copy(
            areaCountry = if (country) null else currentFilter.areaCountry,
            areaCity = if (city) null else currentFilter.areaCity,
            industry = if (industry) null else currentFilter.industry,
            salary = if (salary) null else currentFilter.salary,
            withSalary = if (withSalary) null else currentFilter.withSalary
        )
        saveFilter(updatedFilter)
    }

    override fun clearAll() {
        sharedPrefsUtil.clearAll()
    }
}
