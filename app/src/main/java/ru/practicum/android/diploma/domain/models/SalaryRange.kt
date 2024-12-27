package ru.practicum.android.diploma.domain.models

sealed class SalaryRange {
    object NotSpecified : SalaryRange()
    data class SingleValue(val amount: Number) : SalaryRange()
    data class FromValue(val from: Number) : SalaryRange()
    data class ToValue(val to: Number) : SalaryRange()
    data class Range(val from: Number, val to: Number) : SalaryRange()
}
