package ru.practicum.android.diploma.core.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.core.domain.models.Industry

interface FiltersRepository {
    // примененные фильтры, для экрана поиска
    val filters: Flow<Filters>

    // Настраиваемые фильтры,
    // для экранов Настройки Фильтров,
    // выбор индурстрии,
    // выбор региона
    val tempFilters: Flow<Filters>
    fun applyArea(area: Area?)
    fun applyIndustry(industry: Industry?)
    fun applySalary(salary: String?)

    fun applyToggleSalary()
    fun applyTempFilters()
    fun resetTempFilters()
    fun resetFilters()
}
