package ru.practicum.android.diploma.filter.filter.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettingsModel
import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.place.domain.model.Place

class FilterSPInteractorImpl(private val repository: FilterSPRepository) : FilterSPInteractor { //раз нужен в search, то снимаю internal
    override suspend fun clearDataFilter() {
        repository.clearDataFilter()
    }

    override suspend fun getBranchOfProfessionDataFilter(): Flow<String?> {
        return repository.getBranchOfProfessionDataFilter()
    }

    override suspend fun getExpectedSalaryDataFilter(): Flow<Int?> {
        return repository.getExpectedSalaryDataFilter()
    }

    override suspend fun isDoNotShowWithoutSalaryDataFilter(): Flow<Boolean> {
        return repository.isDoNotShowWithoutSalaryDataFilter()
    }

    override suspend fun getDataFilter(): Flow<FilterSettingsModel> {
        return repository.getDataFilter()
    }

    override suspend fun updatePlaceInDataFilter(place: Place): Flow<Int> {
        return repository.updatePlaceInDataFilter(place)
    }

    override suspend fun updateProfessionInDataFilter(industry: IndustryModel): Flow<Int> {
        return repository.updateProfessionInDataFilter(industry)
    }

    override suspend fun updateSalaryInDataFilter(expectedSalary: Int): Flow<Int> {
        return repository.updateSalaryInDataFilter(expectedSalary)
    }

    override suspend fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Flow<Int> {
        return repository.updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary)
    }

    override suspend fun clearSalaryFilter() {
        repository.clearSalaryFilter()
    }

    override suspend fun clearPlaceFilter() {
        repository.clearPlaceFilter()
    }

    override suspend fun clearIndustryFilter() {
        repository.clearIndustryFilter()
    }


}

