package ru.practicum.android.diploma.data.filters

import android.content.Context
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.domain.filter.datashared.IndustriesShared
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryBooleanShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryTextShared
import ru.practicum.android.diploma.domain.filter.impl.FilterInfoRepositoryImpl.Companion.KEY_COUNTRY
import ru.practicum.android.diploma.domain.filter.impl.FilterInfoRepositoryImpl.Companion.KEY_INDUSTRIES
import ru.practicum.android.diploma.domain.filter.impl.FilterInfoRepositoryImpl.Companion.KEY_REGION
import ru.practicum.android.diploma.domain.filter.impl.FilterInfoRepositoryImpl.Companion.KEY_SALARY_BOOLEAN
import ru.practicum.android.diploma.domain.filter.impl.FilterInfoRepositoryImpl.Companion.KEY_SALARY_TEXT
import ru.practicum.android.diploma.domain.models.Filter

class FiltersRepositoryImpl(
    private val context: Context
) : FiltersRepository {

    private val prefs = context.getSharedPreferences(FILTER_PREFERENCES, Context.MODE_PRIVATE)

    override fun setFilter(filter: Filter) {
        with(prefs.edit()) {
            putString(KEY_COUNTRY, filter.country)
            putString(KEY_REGION, filter.region)
            putString(KEY_INDUSTRIES, filter.industry)
            putString(KEY_SALARY_TEXT, filter.salary)
            filter.onlyWithSalary?.let { putBoolean(KEY_SALARY_BOOLEAN, it) }
        }
    }

    override fun getFilters(): Filter {
        val gson = Gson()

        val country = gson.fromJson(prefs.getString("country", null), CountryShared::class.java)
        val region = gson.fromJson(prefs.getString("region", null), RegionShared::class.java)
        val industry = gson.fromJson(prefs.getString("industries", null), IndustriesShared::class.java)
        val salary = gson.fromJson(prefs.getString("salary_text", "0"), SalaryTextShared::class.java)
        val onlySalary = gson.fromJson(prefs.getString("salary_boolean", null), SalaryBooleanShared::class.java)

        return Filter(
            country = country?.countryId, region = region?.regionId,
            industry = industry?.industriesId, salary = salary?.salary, onlyWithSalary = onlySalary?.isChecked
        )
    }

    companion object {
        private const val FILTER_KEY = "filter"
        private const val FILTER_PREFERENCES = "FilterInfoPrefs"
    }
}
