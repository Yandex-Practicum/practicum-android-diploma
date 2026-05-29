package ru.practicum.android.diploma.core.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.core.ui.navigation.Screen
import ru.practicum.android.diploma.core.domain.models.Industry

interface FiltersRepository {
    val filters: Flow<Filters> //примененные фильтры, для экрана поиска
    val tempFilters: Flow<Filters> //Настраиваемые фильтры, для экранов Настройки Фильтров, выбор индурстрии, выбор региона
    fun applyArea(area: Area?)
    fun applyIndustry(industry: Industry?)
    fun applySalary(salary: String?)

    fun applyToggleSalary()
    fun applyTempFilters()
    fun resetTempFilters()
    fun resetFilters()
}
