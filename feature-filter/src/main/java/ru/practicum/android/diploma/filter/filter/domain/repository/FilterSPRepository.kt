package ru.practicum.android.diploma.filter.filter.domain.repository

interface FilterSPRepository {
    fun putValue(key: String, value: String): Boolean
    fun getValue(key: String): String?
    fun getAll(): Map<String, String>
}
