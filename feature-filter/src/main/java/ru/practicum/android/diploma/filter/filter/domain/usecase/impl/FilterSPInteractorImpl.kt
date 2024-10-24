package ru.practicum.android.diploma.filter.filter.domain.usecase.impl

import ru.practicum.android.diploma.data.sp.api.SpResult
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

    override suspend fun updateDataFilterBuffer(filter: FilterSettings): SpResult {
        return repository.updateDataFilterBuffer(filter)
    }

    override suspend fun updateDataFilter(filter: FilterSettings): SpResult {
        return repository.updateDataFilter(filter)
    }

    override suspend fun clearPlaceInDataFilterBuffer(): SpResult {
        return repository.clearPlaceInDataFilterBuffer()
    }

    override suspend fun clearProfessionInDataFilterBuffer(): SpResult {
        return repository.clearProfessionInDataFilterBuffer()
    }

    override suspend fun updateSalaryInDataFilterBuffer(expectedSalary: String): SpResult {
        return repository.updateSalaryInDataFilterBuffer(expectedSalary)
    }

    override suspend fun updateDoNotShowWithoutSalaryInDataFilterBuffer(doNotShowWithoutSalary: Boolean): SpResult {
        return repository.updateDoNotShowWithoutSalaryInDataFilterBuffer(doNotShowWithoutSalary)
    }
}
