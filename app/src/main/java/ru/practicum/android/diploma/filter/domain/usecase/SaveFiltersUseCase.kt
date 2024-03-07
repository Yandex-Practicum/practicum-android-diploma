package ru.practicum.android.diploma.filter.domain.usecase

import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.FilterType
import ru.practicum.android.diploma.filter.presentation.FilterState

class SaveFiltersUseCase(
    private val filterRepository: FilterRepository
) {
    fun execute(filterState: FilterState) {
        filterState.country?.let {
            filterRepository.saveFilterToLocalStorage(
                FilterType.Country(it.id, it.name)
            )
        }
        filterState.area?.let {
            filterRepository.saveFilterToLocalStorage(
                FilterType.Region(it.id, it.name)
            )
        }
        filterState.salary?.let {
            filterRepository.saveFilterToLocalStorage(
                FilterType.Salary(it)
            )
        }
        filterState.industry?.let {
            filterRepository.saveFilterToLocalStorage(
                FilterType.Industry(it.id, it.name)
            )
        }
        filterRepository.saveFilterToLocalStorage(FilterType.ShowWithSalaryFlag(filterState.isNotShowWithoutSalary))
    }
}
