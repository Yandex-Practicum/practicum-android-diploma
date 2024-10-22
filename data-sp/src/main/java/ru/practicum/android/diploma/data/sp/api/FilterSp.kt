package ru.practicum.android.diploma.data.sp.api

import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.data.sp.dto.IndustryDto
import ru.practicum.android.diploma.data.sp.dto.PlaceDto

@Suppress("TooManyFunctions")
interface FilterSp {
    // filter base
    fun getDataFilter(): FilterDto
    fun updateDataFilter(filterDto: FilterDto): Int

    // filter reconfiguration
    fun copyDataFilterInDataFilterBuffer()
    fun copyDataFilterBufferInDataFilter()

    // filter buffer
    fun getDataFilterBuffer(): FilterDto
    fun updateDataFilterBuffer(filterDto: FilterDto): Int

    fun getPlaceDataFilterBuffer(): PlaceDto?
    fun updatePlaceInDataFilterBuffer(placeDto: PlaceDto): Int
    fun getPlaceDataFilterReserveBuffer(): PlaceDto?
    fun updatePlaceInDataFilterReserveBuffer(placeDto: PlaceDto): Int
    fun clearPlaceFilterBuffer()

    fun getBranchOfProfessionDataFilterBuffer(): IndustryDto?
    fun updateProfessionInDataFilterBuffer(branchOfProfession: IndustryDto): Int
    fun clearIndustryFilterBuffer()

    fun getExpectedSalaryDataFilterBuffer(): String?
    fun updateSalaryInDataFilterBuffer(expectedSalary: String): Int
    fun clearSalaryFilterBuffer()

    fun isDoNotShowWithoutSalaryDataFilterBuffer(): Boolean
    fun updateDoNotShowWithoutSalaryInDataFilterBuffer(doNotShowWithoutSalary: Boolean): Int

    // clear sp
    fun clearDataFilterAll()
}
