package ru.practicum.android.diploma.common.sharedprefs.repository

import ru.practicum.android.diploma.common.sharedprefs.models.City
import ru.practicum.android.diploma.common.sharedprefs.models.Country
import ru.practicum.android.diploma.common.sharedprefs.models.Filter
import ru.practicum.android.diploma.common.sharedprefs.models.Industry

interface SharedPrefsRepository {
    fun saveFilter(filter: Filter)
    fun getFilter(): Filter

    // Указываем значения по умолчанию здесь
    fun updateField(
        country: Country? = null,
        city: City? = null,
        industry: Industry? = null,
        salary: Int? = null,
        withSalary: Boolean? = null
    )

    // Указываем значения по умолчанию здесь
    fun clearField(
        country: Boolean = false,
        city: Boolean = false,
        industry: Boolean = false,
        salary: Boolean = false,
        withSalary: Boolean = false
    )

    fun clearAll()
}
