package ru.practicum.android.diploma.filter.area.domain.usecase

import ru.practicum.android.diploma.filter.area.domain.model.Area
import ru.practicum.android.diploma.filter.domain.api.FilterRepository

class GetAreaFilterUseCase(
    private val filterRepository: FilterRepository
) {
    fun execute(): Area? {
        return filterRepository.getAreaFilterFromLocalStorage()
    }
}
