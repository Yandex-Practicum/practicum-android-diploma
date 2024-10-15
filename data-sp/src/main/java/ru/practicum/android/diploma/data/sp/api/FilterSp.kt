package ru.practicum.android.diploma.data.sp.api

import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.data.sp.dto.PlaceDto

interface FilterSp {
    suspend fun clearDataFilter()
    suspend fun getPlaceDataFilter(): PlaceDto?
    suspend fun getPlaceDataFilterBuffer(): PlaceDto?
    suspend fun getBranchOfProfessionDataFilter(): String?
    suspend fun getExpectedSalaryDataFilter(): String?
    suspend fun isDoNotShowWithoutSalaryDataFilter(): Boolean
    suspend fun getDataFilter(): FilterDto
    suspend fun updatePlaceInDataFilter(placeDto: PlaceDto): Int
    suspend fun updatePlaceInDataFilterBuffer(placeDto: PlaceDto): Int
    suspend fun updateProfessionInDataFilter(branchOfProfession: String?): Int
    suspend fun updateSalaryInDataFilter(expectedSalary: String): Int
    suspend fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Int
    suspend fun clearSalaryFilter()
    suspend fun clearPlaceFilter()
    suspend fun clearIndustryFilter()
}
