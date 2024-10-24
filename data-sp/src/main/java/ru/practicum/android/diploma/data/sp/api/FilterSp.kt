package ru.practicum.android.diploma.data.sp.api

import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.data.sp.dto.IndustryDto
import ru.practicum.android.diploma.data.sp.dto.PlaceDto

@Suppress("TooManyFunctions")
interface FilterSp {
    // filter base
    fun getDataFilter(): FilterDto
    fun updateDataFilter(filterDto: FilterDto): SpResult

    // filter reconfiguration
    fun copyDataFilterInDataFilterBuffer()
    fun copyDataFilterBufferInDataFilter()

    // filter buffer
    fun getDataFilterBuffer(): FilterDto
    fun updateDataFilterBuffer(filterDto: FilterDto): SpResult

    fun getPlaceDataFilterBuffer(): PlaceDto?
    fun updatePlaceInDataFilterBuffer(placeDto: PlaceDto): SpResult
    fun getPlaceDataFilterReserveBuffer(): PlaceDto?
    fun updatePlaceInDataFilterReserveBuffer(placeDto: PlaceDto): SpResult
    fun clearPlaceFilterBuffer()

    fun getBranchOfProfessionDataFilterBuffer(): IndustryDto?
    fun updateProfessionInDataFilterBuffer(branchOfProfession: IndustryDto): SpResult
    fun clearIndustryFilterBuffer()

    fun getExpectedSalaryDataFilterBuffer(): String?
    fun updateSalaryInDataFilterBuffer(expectedSalary: String): SpResult
    fun clearSalaryFilterBuffer()

    fun isDoNotShowWithoutSalaryDataFilterBuffer(): Boolean
    fun updateDoNotShowWithoutSalaryInDataFilterBuffer(doNotShowWithoutSalary: Boolean): SpResult

    // clear sp
    fun clearDataFilterAll()

    // tech utils
    fun forceSearch()
    fun disableForceSearch()
    fun isForceSearchEnabled(): Boolean
}
