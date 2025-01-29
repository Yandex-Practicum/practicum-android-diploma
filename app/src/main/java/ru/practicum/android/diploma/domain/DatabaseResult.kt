package ru.practicum.android.diploma.domain

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class DatabaseResult {
    data class Success(val vacancies: List<Vacancy>) : DatabaseResult()
    data class Error(val message: String) : DatabaseResult()
    data object Empty : DatabaseResult()
}
