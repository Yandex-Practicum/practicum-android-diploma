package ru.practicum.android.diploma.core.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.core.data.FilterDto
import ru.practicum.android.diploma.core.data.FiltersDto
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.core.domain.models.Industry
import ru.practicum.android.diploma.core.domain.repository.FiltersRepository

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
    private val _tempFilters = MutableStateFlow(Filters())
    override val tempFilters: Flow<Filters> = _tempFilters.asStateFlow()
    init {
        initFilters()
    }

    override fun applyArea(area: Area?) {
        _tempFilters.value = _tempFilters.value.copy(area = area)
    }

    override fun applyIndustry(industry: Industry?) {
        _tempFilters.value = _tempFilters.value.copy(industry = industry)
    }

    override fun applySalary(salary: String?) {
        _tempFilters.value = _tempFilters.value.copy(salary = salary)
    }

    override fun applyToggleSalary() {
        _tempFilters.value = _tempFilters.value.copy(onlyWithSalary = !_tempFilters.value.onlyWithSalary)
    }

    override fun applyTempFilters() {
        val area = _tempFilters.value.area
        val industry = _tempFilters.value.industry
        val intSalary = _tempFilters.value.salary?.trim()?.toInt()
        val onlyWithSalary = _tempFilters.value.onlyWithSalary

        var dto = filtersDto
        if (area != null) {
            dto = dto.copy(area = FilterDto(id = area.id, name = area.name))
        } else {
            dto = dto.copy(area = null)
        }
        if (industry != null) {
            dto = dto.copy(industry = FilterDto(id = industry.id, name = industry.name))
        } else {
            dto = dto.copy(industry = null)
        }
        _filters.value = _tempFilters.value
        filtersDto = dto.copy(salary = intSalary, onlyWithSalary = onlyWithSalary)
    }

    override fun resetTempFilters() {
        _tempFilters.value = _filters.value
    }

    override fun resetFilters() {
        _tempFilters.value = Filters()
    }

    private fun initFilters() {
        var dto = filtersDto
        _filters.value = Filters(
            area = if (dto.area != null) Area(dto.area.id, dto.area.name) else null,
            industry = if (dto.industry != null) Industry(dto.industry.id, dto.industry.name) else null,
            salary = if (dto.salary != null) dto.salary.toString() else null,
            onlyWithSalary = dto.onlyWithSalary
        )
        _tempFilters.value = _filters.value
    }

    companion object {
        const val FILTERS_PREFERENCES_KEY = "FILTERS_PREFERENCES_KEY"
    }
}
