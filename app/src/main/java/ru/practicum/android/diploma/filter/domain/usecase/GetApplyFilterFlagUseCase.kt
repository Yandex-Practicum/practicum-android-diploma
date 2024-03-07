package ru.practicum.android.diploma.filter.domain.usecase

import ru.practicum.android.diploma.filter.domain.api.FilterRepository

class GetApplyFilterFlagUseCase(
    private val filterRepository: FilterRepository
) {
    fun execute(): Boolean {
        return filterRepository.isFilterApplied()
    }
}
