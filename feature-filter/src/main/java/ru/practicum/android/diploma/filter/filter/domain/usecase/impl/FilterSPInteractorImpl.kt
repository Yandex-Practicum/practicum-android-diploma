package ru.practicum.android.diploma.filter.filter.domain.usecase.impl

import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository
import ru.practicum.android.diploma.filter.filter.domain.usecase.FilterSPInteractor

class FilterSPInteractorImpl(private val repository: FilterSPRepository) : FilterSPInteractor {

    override suspend fun clearDataFilter() {
        return repository.clearDataFilter()
    }

    override suspend fun getExpectedSalaryDataFilter(): String? {
        return repository.getExpectedSalaryDataFilter()
    }

    override suspend fun isDoNotShowWithoutSalaryDataFilter(): Boolean {
        return repository.isDoNotShowWithoutSalaryDataFilter()
    }

    override suspend fun getDataFilter(): FilterSettings {
        return repository.getDataFilter()
    }

    override suspend fun clearPlaceInDataFilter(): Int {
        return repository.clearPlaceInDataFilter()
    }

    override suspend fun clearProfessionInDataFilter(): Int {
        return repository.clearProfessionInDataFilter()
    }

    override suspend fun updateSalaryInDataFilter(expectedSalary: String): Int {
        return repository.updateSalaryInDataFilter(expectedSalary)
    }

    override suspend fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Int {
        return repository.updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary)
    }
}
