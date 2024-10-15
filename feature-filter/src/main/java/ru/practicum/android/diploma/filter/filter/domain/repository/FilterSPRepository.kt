package ru.practicum.android.diploma.filter.filter.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettingsModel
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.place.domain.model.Place

interface FilterSPRepository {
    suspend fun clearDataFilter()
    //suspend fun getPlaceDataFilter(): Flow<Place?> todo delete не нужен
    suspend fun getBranchOfProfessionDataFilter(): Flow<String?>
    suspend fun getExpectedSalaryDataFilter(): Flow<Int?>
    suspend fun isDoNotShowWithoutSalaryDataFilter(): Flow<Boolean>
    suspend fun getDataFilter(): Flow<FilterSettingsModel>
    suspend fun updatePlaceInDataFilter(place: Place): Flow<Int>
    suspend fun updateProfessionInDataFilter(industryModel: IndustryModel): Flow<Int>
    suspend fun updateSalaryInDataFilter(expectedSalary: Int): Flow<Int>
    suspend fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Flow<Int>
    suspend fun clearSalaryFilter()
    suspend fun clearPlaceFilter()
    suspend fun clearIndustryFilter()
}
