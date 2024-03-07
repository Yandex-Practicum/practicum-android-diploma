package ru.practicum.android.diploma.core.data.storage.sharedpreferences

import android.content.SharedPreferences
import ru.practicum.android.diploma.filter.domain.models.FilterType

class FilterStorageImpl(private val sharedPreferences: SharedPreferences) : FilterStorage {

    override fun saveFilters(filters: List<FilterType>) {
        with(sharedPreferences.edit()) {
            filters.forEach { filter ->
                when (filter) {
                    is FilterType.Country -> {
                        putString(COUNTRY_ID_KEY, filter.id)
                        putString(COUNTRY_NAME_KEY, filter.name)
                    }

                    is FilterType.Region -> {
                        putString(REGION_ID_KEY, filter.id)
                        putString(REGION_NAME_KEY, filter.name)
                    }

                    is FilterType.Industry -> {
                        putString(INDUSTRY_ID_KEY, filter.id)
                        putString(INDUSTRY_NAME_KEY, filter.name)
                    }

                    is FilterType.Salary -> {
                        putInt(SALARY_AMOUNT_KEY, filter.amount)
                    }

                    is FilterType.ShowWithSalaryFlag -> {
                        putBoolean(SHOW_WITH_SALARY_FLAG_KEY, filter.flag)
                    }
                }
            }
            apply()
        }
    }

    override fun getFilters(): List<FilterType> {
        val filters = mutableListOf<FilterType>()

        addCountryFilter(filters)
        addRegionFilter(filters)
        addIndustryFilter(filters)
        addSalaryFilter(filters)
        addShowWithSalaryFlagFilter(filters)

        return filters
    }

    private fun addCountryFilter(filters: MutableList<FilterType>) {
        sharedPreferences.apply {
            val countryId = getString(COUNTRY_ID_KEY, "")
            val countryName = getString(COUNTRY_NAME_KEY, "") ?: ""
            if (countryId != "" && countryName.isNotBlank()) {
                filters.add(FilterType.Country(countryId ?: "", countryName))
            }
        }
    }

    private fun addRegionFilter(filters: MutableList<FilterType>) {
        sharedPreferences.apply {
            val regionId = getString(REGION_ID_KEY, "")
            val regionName = getString(REGION_NAME_KEY, "") ?: ""
            if (regionId != "" && regionName.isNotBlank()) {
                filters.add(FilterType.Region(regionId ?: "", regionName))
            }
        }
    }

    private fun addIndustryFilter(filters: MutableList<FilterType>) {
        sharedPreferences.apply {
            val industryId = getString(INDUSTRY_ID_KEY, "")
            val industryName = getString(INDUSTRY_NAME_KEY, "") ?: ""
            if (industryId != "" && industryName.isNotBlank()) {
                filters.add(FilterType.Industry(industryId ?: "", industryName))
            }
        }
    }

    private fun addSalaryFilter(filters: MutableList<FilterType>) {
        sharedPreferences.apply {
            val salaryAmount = getInt(SALARY_AMOUNT_KEY, 0)
            if (salaryAmount != 0) {
                filters.add(FilterType.Salary(salaryAmount))
            }
        }
    }

    private fun addShowWithSalaryFlagFilter(filters: MutableList<FilterType>) {
        sharedPreferences.apply {
            val showWithSalaryFlag = getBoolean(SHOW_WITH_SALARY_FLAG_KEY, false)
            filters.add(FilterType.ShowWithSalaryFlag(showWithSalaryFlag))
        }
    }

    override fun deleteFilter(filterType: FilterType) {
        when (filterType) {
            is FilterType.Country -> {
                with(sharedPreferences.edit()) {
                    remove(COUNTRY_ID_KEY)
                    remove(COUNTRY_NAME_KEY)
                    apply()
                }
            }

            is FilterType.Region -> {
                with(sharedPreferences.edit()) {
                    remove(REGION_ID_KEY)
                    remove(REGION_NAME_KEY)
                    apply()
                }
            }

            is FilterType.Industry -> {
                with(sharedPreferences.edit()) {
                    remove(INDUSTRY_ID_KEY)
                    remove(INDUSTRY_NAME_KEY)
                    apply()
                }
            }

            is FilterType.Salary -> {
                with(sharedPreferences.edit()) {
                    remove(SALARY_AMOUNT_KEY)
                    apply()
                }
            }

            is FilterType.ShowWithSalaryFlag -> {
                with(sharedPreferences.edit()) {
                    remove(SHOW_WITH_SALARY_FLAG_KEY)
                    apply()
                }
            }
        }
    }

    override fun clearFilters() {
        with(sharedPreferences.edit()) {
            remove(COUNTRY_ID_KEY)
            remove(COUNTRY_NAME_KEY)
            remove(REGION_ID_KEY)
            remove(REGION_NAME_KEY)
            remove(INDUSTRY_ID_KEY)
            remove(INDUSTRY_NAME_KEY)
            remove(SALARY_AMOUNT_KEY)
            remove(SHOW_WITH_SALARY_FLAG_KEY)
            remove(IS_FILTER_APPLIED)
            apply()
        }
    }

    override fun isFilterApplied(): Boolean {
        return sharedPreferences.getBoolean(IS_FILTER_APPLIED, false)
    }

    override fun setFilterApplied(isApplied: Boolean) {
        sharedPreferences.edit().putBoolean(IS_FILTER_APPLIED, isApplied).apply()
    }

    companion object {
        private const val COUNTRY_ID_KEY = "country_id"
        private const val COUNTRY_NAME_KEY = "country_name"
        private const val REGION_ID_KEY = "region_id"
        private const val REGION_NAME_KEY = "region_name"
        private const val INDUSTRY_ID_KEY = "industry_id"
        private const val INDUSTRY_NAME_KEY = "industry_name"
        private const val SALARY_AMOUNT_KEY = "salary_amount"
        private const val SHOW_WITH_SALARY_FLAG_KEY = "show_with_salary_flag"
        private const val IS_FILTER_APPLIED = "IS_FILTER_APPLIED"
    }
}
