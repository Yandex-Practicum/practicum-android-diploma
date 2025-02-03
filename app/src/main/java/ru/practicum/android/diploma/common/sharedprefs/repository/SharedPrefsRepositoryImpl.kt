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
            withSalary = updatedFilter.withSalary ?: currentFilter.withSalary
        )
        sharedPrefsUtil.saveFilter(mergedFilter)
    }

    override fun clearFilter() {
        sharedPrefsUtil.clearAll()
    }

    override fun getFilter(): Filter {
        return sharedPrefsUtil.getFilter()
    }
}
