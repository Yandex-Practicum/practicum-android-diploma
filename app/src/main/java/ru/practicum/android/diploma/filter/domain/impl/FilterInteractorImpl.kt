package ru.practicum.android.diploma.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.core.domain.repository.FiltersRepository
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor

class FilterInteractorImpl(val repository: FiltersRepository): FilterInteractor {
    override fun filters(): Flow<Filters> = flow {
        repository.filters.collect {
            emit(it)
        }
    }

    override fun resetIndustry() {
        repository.applyIndustry(null)
    }

    override fun resetArea() {
        repository.applyIndustry(null)
    }

    override fun changeSalary(value: String) {

    }
    override fun toggleSalary(){

    }
}
