package ru.practicum.android.diploma.filter.filter.domain.usecase.impl

import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository
import ru.practicum.android.diploma.filter.filter.domain.usecase.FilterSPInteractor

class FilterSPInteractorImpl(private val repository: FilterSPRepository) : FilterSPInteractor {

    override fun clearDataFilter() {
        return repository.clearDataFilter()
    }

    override fun getExpectedSalaryDataFilter(): String? {
        return repository.getExpectedSalaryDataFilter()
    }

    override fun isDoNotShowWithoutSalaryDataFilter(): Boolean {
        return repository.isDoNotShowWithoutSalaryDataFilter()
    }

    override fun getDataFilter(): FilterSettings {
        return repository.getDataFilter()
    }

    override fun clearPlaceInDataFilter(): Int {
        return repository.clearPlaceInDataFilter()
    }

    override fun clearProfessionInDataFilter(): Int {
        return repository.clearProfessionInDataFilter()
    }

    override fun updateSalaryInDataFilter(expectedSalary: String): Int {
        return repository.updateSalaryInDataFilter(expectedSalary)
    }

    override fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Int {
        return repository.updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary)
    }
}
