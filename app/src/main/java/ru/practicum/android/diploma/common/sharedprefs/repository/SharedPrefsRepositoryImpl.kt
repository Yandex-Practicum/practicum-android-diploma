package ru.practicum.android.diploma.common.sharedprefs.repository

import ru.practicum.android.diploma.common.sharedprefs.SharedPrefsUtil
import ru.practicum.android.diploma.common.sharedprefs.models.Filter

class SharedPrefsRepositoryImpl(private val sharedPrefsUtil: SharedPrefsUtil) : SharedPrefsRepository {

    override fun updateFilter(updatedFilter: Filter) {
        val currentFilter = sharedPrefsUtil.getFilter()

        val mergedFilter = Filter(
            areaCountry = updatedFilter.areaCountry ?: currentFilter.areaCountry,
            areaCity = updatedFilter.areaCity ?: currentFilter.areaCity,
            industrySP = updatedFilter.industrySP ?: currentFilter.industrySP,
            salary = updatedFilter.salary ?: currentFilter.salary,
            withSalary = if (updatedFilter.withSalary == false) currentFilter.withSalary else updatedFilter.withSalary
        )
        println(updatedFilter)
        sharedPrefsUtil.saveFilter(mergedFilter)
    }

    override fun clearFilterField(field: String) {
        val currentFilter = sharedPrefsUtil.getFilter()

        val updatedFilter = when (field) {
            "areaCountry" -> currentFilter.copy(areaCountry = null)
            "areaCity" -> currentFilter.copy(areaCity = null)
            "industrySP" -> currentFilter.copy(industrySP = null)
            "salary" -> currentFilter.copy(salary = null)
            "withSalary" -> currentFilter.copy(withSalary = false)
            else -> currentFilter
        }

        sharedPrefsUtil.saveFilter(updatedFilter)
    }

    override fun clearFilter() {
        sharedPrefsUtil.clearAll()
    }

    override fun getFilter(): Filter {
        return sharedPrefsUtil.getFilter()
    }
}
