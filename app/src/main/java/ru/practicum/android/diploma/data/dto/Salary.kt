package ru.practicum.android.diploma.data.dto

data class Salary(
    var currency: String? = null,
    var from: Int? = null,
    var gross: Boolean? = null,
    var to: String? = null
)
