package ru.practicum.android.diploma.filter.filter.domain.usecase.impl

import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository
import ru.practicum.android.diploma.filter.filter.domain.usecase.FilterSPInteractor

internal class FilterSPInteractorImpl(private val repository: FilterSPRepository) : FilterSPInteractor {

    override suspend fun clearDataFilterAll() {
        return repository.clearDataFilterAll()
    }

    override suspend fun getExpectedSalaryDataFilterBuffer(): String? {
        return repository.getExpectedSalaryDataFilterBuffer()
    }

    override suspend fun isDoNotShowWithoutSalaryDataFilterBuffer(): Boolean {
        return repository.isDoNotShowWithoutSalaryDataFilterBuffer()
    }

    override suspend fun getDataFilter(): FilterSettings {
        return repository.getDataFilter()
    }

    override suspend fun getDataFilterBuffer(): FilterSettings {
        return repository.getDataFilterBuffer()
    }

    override suspend fun copyDataFilterInDataFilterBuffer() {
        repository.copyDataFilterInDataFilterBuffer()
    }

    override suspend fun copyDataFilterBufferInDataFilter() {
        repository.copyDataFilterBufferInDataFilter()
    }

    override suspend fun updateDataFilterBuffer(filter: FilterSettings): Int {
        return repository.updateDataFilterBuffer(filter)
    }

    override suspend fun updateDataFilter(filter: FilterSettings): Int {
        return repository.updateDataFilter(filter)
    }

    override suspend fun clearPlaceInDataFilterBuffer(): Int {
        return repository.clearPlaceInDataFilterBuffer()
    }

    override suspend fun clearProfessionInDataFilterBuffer(): Int {
        return repository.clearProfessionInDataFilterBuffer()
    }

    override suspend fun updateSalaryInDataFilterBuffer(expectedSalary: String): Int {
        return repository.updateSalaryInDataFilterBuffer(expectedSalary)
    }

    override suspend fun updateDoNotShowWithoutSalaryInDataFilterBuffer(doNotShowWithoutSalary: Boolean): Int {
        return repository.updateDoNotShowWithoutSalaryInDataFilterBuffer(doNotShowWithoutSalary)
    }

    override suspend fun setForceSearch() {
        repository.setForceSearch()
    }

    override suspend fun dropForceSearch() {
        repository.dropForceSearch()
    }
}
