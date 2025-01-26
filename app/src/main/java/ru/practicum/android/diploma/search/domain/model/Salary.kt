package ru.practicum.android.diploma.search.domain.model

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

data class Salary(
    val from: Int? = null,
    val to: Int? = null,
    val currency: String,
)
