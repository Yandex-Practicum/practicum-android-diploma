package ru.practicum.android.diploma.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.models.Filters

interface FilterInteractor {
    fun filters(): Flow<Filters>
    fun resetIndustry()
    fun resetArea()
    fun changeSalary(value: String?)
    fun toggleSalary()
    fun apply()
    fun resetTemp()
    fun reset()
}
