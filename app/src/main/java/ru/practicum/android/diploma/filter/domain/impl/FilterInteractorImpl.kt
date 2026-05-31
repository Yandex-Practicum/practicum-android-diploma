package ru.practicum.android.diploma.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.core.domain.repository.FiltersRepository
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor

class FilterInteractorImpl(val repository: FiltersRepository) : FilterInteractor {
    override fun filters(): Flow<Filters> = flow {
        repository.tempFilters.collect {
            emit(it)
        }
    }

    override fun resetIndustry() {
        repository.applyIndustry(null)
    }

    override fun resetArea() {
        repository.applyArea(null)
    }

    override fun changeSalary(value: String?) {
        repository.applySalary(value)
    }
    override fun toggleSalary() {
        repository.applyToggleSalary()
    }

    override fun apply() {
        repository.applyTempFilters()
    }

    override fun resetTemp() {
        repository.resetTempFilters()
    }
    override fun reset() {
        repository.resetFilters()
    }
}
