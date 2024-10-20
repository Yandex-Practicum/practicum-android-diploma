package ru.practicum.android.diploma.data.sp.api

import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.data.sp.dto.IndustryDto
import ru.practicum.android.diploma.data.sp.dto.PlaceDto

@Suppress("detekt.TooManyFunctions")
interface FilterSp {
    fun clearDataFilter()
    fun getPlaceDataFilter(): PlaceDto?
    fun getPlaceDataFilterBuffer(): PlaceDto?
    fun getBranchOfProfessionDataFilter(): IndustryDto?
    fun getExpectedSalaryDataFilter(): String?
    fun isDoNotShowWithoutSalaryDataFilter(): Boolean
    fun getDataFilter(): FilterDto
    fun updatePlaceInDataFilter(placeDto: PlaceDto): Int
    fun updatePlaceInDataFilterBuffer(placeDto: PlaceDto): Int
    fun updateProfessionInDataFilter(branchOfProfession: IndustryDto): Int
    fun updateSalaryInDataFilter(expectedSalary: String): Int
    fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Int
    fun clearSalaryFilter()
    fun clearPlaceFilter()
    fun clearIndustryFilter()
}
