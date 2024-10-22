package ru.practicum.android.diploma.filter.filter.domain.repository

import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings

@Suppress("TooManyFunctions")
internal interface FilterSPRepository {

    suspend fun clearDataFilterAll()

    suspend fun getExpectedSalaryDataFilterBuffer(): String?
    suspend fun isDoNotShowWithoutSalaryDataFilterBuffer(): Boolean

    suspend fun getDataFilter(): FilterSettings
    suspend fun getDataFilterBuffer(): FilterSettings

    suspend fun copyDataFilterInDataFilterBuffer()
    suspend fun copyDataFilterBufferInDataFilter()

    suspend fun updateDataFilterBuffer(filter: FilterSettings): Int
    suspend fun updateDataFilter(filter: FilterSettings): Int

    suspend fun clearPlaceInDataFilterBuffer(): Int
    suspend fun clearProfessionInDataFilterBuffer(): Int

    suspend fun updateSalaryInDataFilterBuffer(expectedSalary: String): Int
    suspend fun updateDoNotShowWithoutSalaryInDataFilterBuffer(doNotShowWithoutSalary: Boolean): Int
}
