package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.data.dto.model.SalaryDto
import ru.practicum.android.diploma.domain.models.SalaryRange

class SalaryItem {
    fun salaryDetermine(item: SalaryDto?): SalaryRange {
        return when {
            item == null || item.to == null && item.from == null -> SalaryRange.NotSpecified
            item.from != null && item.to != null && item.from == item.to -> SalaryRange.SingleValue(item.from)
            item.from != null && item.to == null -> SalaryRange.FromValue(item.from)
            item.from == null && item.to != null -> SalaryRange.ToValue(item.to)
            else -> SalaryRange.Range(item.from ?: 0, item.to ?: 0)
        }
    }
}
