package ru.practicum.android.diploma.filter.industry.domain.usecase

import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.FilterType
import ru.practicum.android.diploma.filter.industry.domain.model.Industry

class SaveIndustryUseCase(
    private val filterRepository: FilterRepository
) {
    fun execute(industry: Industry) {
        filterRepository.saveFilterToLocalStorage(
            FilterType.Industry(
                industry.id,
                industry.name
            )
        )
    }
}
