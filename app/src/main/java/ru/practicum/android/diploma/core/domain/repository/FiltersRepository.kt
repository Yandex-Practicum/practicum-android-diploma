package ru.practicum.android.diploma.core.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.industry.domain.models.Industry

interface FiltersRepository {
    val filters: Flow<Map<String, String>>
    fun applyIndustry(industry: Industry?)
    fun applySalary(salary: Int?)
}
