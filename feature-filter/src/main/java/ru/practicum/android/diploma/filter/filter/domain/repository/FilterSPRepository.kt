package ru.practicum.android.diploma.filter.filter.domain.repository

import ru.practicum.android.diploma.data.sp.api.SpResult
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

    suspend fun updateDataFilterBuffer(filter: FilterSettings): SpResult
    suspend fun updateDataFilter(filter: FilterSettings): SpResult

    suspend fun clearPlaceInDataFilterBuffer(): SpResult
    suspend fun clearProfessionInDataFilterBuffer(): SpResult

    suspend fun updateSalaryInDataFilterBuffer(expectedSalary: String): SpResult
    suspend fun updateDoNotShowWithoutSalaryInDataFilterBuffer(doNotShowWithoutSalary: Boolean): SpResult
}
