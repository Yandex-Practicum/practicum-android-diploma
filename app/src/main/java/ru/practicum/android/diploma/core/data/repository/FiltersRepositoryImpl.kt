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

    override fun applyCountry(country: Area?) {
        _tempFilters.value = _tempFilters.value.copy(country = country)
    }

    override fun applyRegion(region: Area?) {
        _tempFilters.value = _tempFilters.value.copy(region = region)
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
        val country = _tempFilters.value.country
        val region = _tempFilters.value.region
        val industry = _tempFilters.value.industry
        val intSalary = _tempFilters.value.salary?.trim()?.toIntOrNull()
        val onlyWithSalary = _tempFilters.value.onlyWithSalary

        var dto = filtersDto
        if (country != null) {
            dto = dto.copy(country = FilterDto(id = country.id, name = country.name))
        } else {
            dto = dto.copy(country = null)
        }

        if (region != null) {
            dto = dto.copy(region = FilterDto(id = region.id, name = region.name))
        } else {
            dto = dto.copy(region = null)
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
            country = if (dto.country != null) Area(dto.country.id, dto.country.name) else null,
            region = if (dto.region != null) Area(dto.region.id, dto.region.name) else null,
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
