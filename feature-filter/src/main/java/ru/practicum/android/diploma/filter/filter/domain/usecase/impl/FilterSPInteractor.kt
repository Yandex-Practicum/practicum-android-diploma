package ru.practicum.android.diploma.filter.filter.domain.usecase.impl

import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettingsModel
import ru.practicum.android.diploma.filter.place.domain.model.Place

interface FilterSPInteractor {
    suspend fun clearDataFilter()
    suspend fun getBranchOfProfessionDataFilter(): String?
    suspend fun getExpectedSalaryDataFilter(): Int?
    suspend fun isDoNotShowWithoutSalaryDataFilter(): Boolean
    suspend fun getDataFilter(): FilterSettingsModel
    suspend fun updatePlaceInDataFilter(place: Place): Int
    suspend fun updateProfessionInDataFilter(industry : String): Int
    suspend fun updateSalaryInDataFilter(expectedSalary: Int): Int
    suspend fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Int
    suspend fun clearSalaryFilter()
    suspend fun clearPlaceFilter()
    suspend fun clearIndustryFilter()
}

