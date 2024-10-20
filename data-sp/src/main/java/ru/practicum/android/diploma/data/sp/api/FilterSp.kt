package ru.practicum.android.diploma.data.sp.api

import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.data.sp.dto.IndustryDto
import ru.practicum.android.diploma.data.sp.dto.PlaceDto

@Suppress("detekt.TooManyFunctions")
interface FilterSp {
    suspend fun getPlaceDataFilter(): PlaceDto?
    suspend fun getPlaceDataFilterBuffer(): PlaceDto?
    suspend fun getBranchOfProfessionDataFilter(): IndustryDto?
    suspend fun getExpectedSalaryDataFilter(): String?
    suspend fun isDoNotShowWithoutSalaryDataFilter(): Boolean
    suspend fun getDataFilter(): FilterDto
    suspend fun getDataFilterBuffer(): FilterDto
    suspend fun copyDataFilterInDataFilterBuffer()
    suspend fun copyDataFilterBufferInDataFilter()
    suspend fun updateDataFilterBuffer(filterDto: FilterDto): Int
    suspend fun updateDataFilter(filterDto: FilterDto): Int
    suspend fun updatePlaceInDataFilter(placeDto: PlaceDto): Int
    suspend fun updatePlaceInDataFilterBuffer(placeDto: PlaceDto): Int
    suspend fun updateProfessionInDataFilter(branchOfProfession: IndustryDto): Int
    suspend fun updateSalaryInDataFilter(expectedSalary: String): Int
    suspend fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Int
    suspend fun clearDataFilter()
    suspend fun clearSalaryFilter()
    suspend fun clearPlaceFilter()
    suspend fun clearIndustryFilter()
}
