package ru.practicum.android.diploma.filter.filter.domain.repository

import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettingsModel
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.place.domain.model.Place

interface FilterSPRepository {
    suspend fun clearDataFilter()
    //suspend fun getPlaceDataFilter(): Place? todo delete не нужен
    suspend fun getBranchOfProfessionDataFilter(): String?
    suspend fun getExpectedSalaryDataFilter(): Int?
    suspend fun isDoNotShowWithoutSalaryDataFilter(): Boolean
    suspend fun getDataFilter(): FilterSettingsModel
    suspend fun updatePlaceInDataFilter(place: Place): Int
    suspend fun updateProfessionInDataFilter(industryModel: IndustryModel): Int
    suspend fun updateSalaryInDataFilter(expectedSalary: Int): Int
    suspend fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Int
    suspend fun clearSalaryFilter()
    suspend fun clearPlaceFilter()
    suspend fun clearIndustryFilter()
}
