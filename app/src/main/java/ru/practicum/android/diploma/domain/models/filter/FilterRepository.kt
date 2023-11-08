package ru.practicum.android.diploma.domain.models.filter

interface FilterRepository {
    fun setSalary(input: String)
    fun getSalary(): String
}