package ru.practicum.android.diploma.search.domain

import android.icu.util.Currency

data class Vacancy(
    val title: String,
    val companyName: String,
    val salary: Int?,
    val salaryCurrency: String?,
    val companyLogo: String?
)
