package ru.practicum.android.diploma.filter.area.domain.usecase

import ru.practicum.android.diploma.filter.area.domain.model.Area
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.FilterType

class SaveAreaUseCase(
    private val filterRepository: FilterRepository
) {
    fun execute(area: Area) {
        filterRepository.saveFilterToLocalStorage(FilterType.Region(
            id = area.id,
            name = area.name
        ))
    }
}
