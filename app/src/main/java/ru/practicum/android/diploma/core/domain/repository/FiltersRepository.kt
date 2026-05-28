package ru.practicum.android.diploma.core.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.core.ui.navigation.Screen
import ru.practicum.android.diploma.core.domain.models.Industry

interface FiltersRepository {
    val filters: Flow<Filters>
    fun applyArea(area: Screen.Area?)
    fun applyIndustry(industry: Industry?)
    fun applySalary(salary: Int?)

    fun applyToggleSalary(toggle: Boolean)
}
