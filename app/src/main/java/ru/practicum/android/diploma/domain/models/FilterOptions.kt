package ru.practicum.android.diploma.domain.models

import android.icu.util.Currency

data class FilterOptions(
    val text: String,
    val area: String,
    val industry: String,
    val currency: String,
    val salary: String,
    val onlyWithSalary: String
)
