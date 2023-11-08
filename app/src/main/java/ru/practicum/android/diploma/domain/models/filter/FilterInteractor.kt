package ru.practicum.android.diploma.domain.models.filter

interface FilterInteractor {
    fun setSalary(input: String)

    fun getSalary(): String
}