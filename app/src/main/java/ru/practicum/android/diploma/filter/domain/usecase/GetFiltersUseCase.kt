package ru.practicum.android.diploma.filter.domain.usecase

import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.FilterType

class GetFiltersUseCase(
    private val filterRepository: FilterRepository
) {
    fun execute(): List<FilterType> {
        return filterRepository.getFilters()
    }
}
