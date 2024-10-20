package ru.practicum.android.diploma.filter.filter.domain.usecase

import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings

interface FilterSPInteractor {
    fun clearDataFilter()

    fun getExpectedSalaryDataFilter(): String?
    fun isDoNotShowWithoutSalaryDataFilter(): Boolean

    fun getDataFilter(): FilterSettings

    fun clearPlaceInDataFilter(): Int
    fun clearProfessionInDataFilter(): Int
    fun updateSalaryInDataFilter(expectedSalary: String): Int
    fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Int
}
