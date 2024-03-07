package ru.practicum.android.diploma.filter.domain.usecase

import ru.practicum.android.diploma.filter.domain.api.FilterRepository

class ApplyFilterUseCase(
    private val filterRepository: FilterRepository
) {
    fun execute() {
        filterRepository.setFilterApplied(true)
    }
}
