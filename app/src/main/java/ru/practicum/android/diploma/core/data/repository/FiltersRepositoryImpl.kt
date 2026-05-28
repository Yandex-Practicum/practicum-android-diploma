package ru.practicum.android.diploma.core.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.core.data.FiltersDto
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.core.domain.repository.FiltersRepository
import ru.practicum.android.diploma.core.ui.navigation.Screen
import ru.practicum.android.diploma.core.domain.models.Industry

class FiltersRepositoryImpl(val context: Context) : FiltersRepository {
    private var filtersDto: FiltersDto get() {
        try {
            val json = filtersPreferences.getString(FILTERS_PREFERENCES_KEY, "")
            val type = object : TypeToken<FiltersDto>() {}.type
            return Gson().fromJson(json, type)
        } catch (_: Exception) {
            return FiltersDto()
        }
    }

        set(value) {
            val json = Gson().toJson(value)
            filtersPreferences.edit {
                putString(FILTERS_PREFERENCES_KEY, json)
            }
        }

    private var filtersPreferences: SharedPreferences
        get() = context.getSharedPreferences(FILTERS_PREFERENCES_KEY, Context.MODE_PRIVATE)
        set(value) {}
    private var _filters = MutableStateFlow(Filters())
    override val filters: Flow<Filters> = _filters.asStateFlow()
    init {
        updateFilters()
    }
    override fun applyArea(area: Screen.Area?) {
        if (filtersDto.area != area?.id) {
            filtersDto = filtersDto.copy(industry = industry?.id)
            updateFilters()
        }
    }
    override fun applyIndustry(industry: Industry?) {
        if (filtersDto.industry != industry?.id) {
            filtersDto = filtersDto.copy(industry = industry?.id)
            updateFilters()
        }
    }

    override fun applySalary(salary: Int?) {
        if (filtersDto.salary != salary) {
            filtersDto = filtersDto.copy(salary = salary)
            updateFilters()
        }
    }

    override fun applyToggleSalary(toggle: Boolean) {
        filtersDto = filtersDto.copy(onlyWithSalary = !filtersDto.onlyWithSalary)
        updateFilters()
    }

    private fun updateFilters() {
//        var tempFilters = mutableMapOf<String, String>()
//        filtersDto.industry?.let {
//            tempFilters[INDUSTRY_MAP_KEY] = it
//        }
//
//        filtersDto.salary?.let {
//            tempFilters[SALARY_MAP_KEY] = it.toString()
//        }

        _filters.value = Filters(
            industry = filtersDto.industry,
            salary = if (filtersDto.salary != null) filtersDto.salary.toString() else null
        )
    }

    companion object {
        const val FILTERS_PREFERENCES_KEY = "FILTERS_PREFERENCES_KEY"
        const val INDUSTRY_MAP_KEY = "industry"
        const val SALARY_MAP_KEY = "salary"
    }
}
