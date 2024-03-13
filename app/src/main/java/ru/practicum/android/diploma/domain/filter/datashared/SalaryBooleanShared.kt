package ru.practicum.android.diploma.domain.filter.datashared

import kotlinx.serialization.Serializable

@Serializable
data class SalaryBooleanShared(
    val isChecked: Boolean?
)
