package ru.practicum.android.diploma.filter.filter.domain.usecase.impl

interface FilterSPInteractor {
    fun putValue(key: String, value: String): Boolean
    fun getValue(key: String): String?
    fun getAll(): Map<String, String>
}
