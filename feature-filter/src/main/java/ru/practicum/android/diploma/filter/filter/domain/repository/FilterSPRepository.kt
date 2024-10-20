package ru.practicum.android.diploma.filter.filter.domain.repository

import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings

interface FilterSPRepository {

    fun clearDataFilter()

    fun getExpectedSalaryDataFilter(): String?
    fun isDoNotShowWithoutSalaryDataFilter(): Boolean

    fun getDataFilter(): FilterSettings

    fun clearPlaceInDataFilter(): Int
    fun clearProfessionInDataFilter(): Int
    fun updateSalaryInDataFilter(expectedSalary: String): Int
    fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Int
}
