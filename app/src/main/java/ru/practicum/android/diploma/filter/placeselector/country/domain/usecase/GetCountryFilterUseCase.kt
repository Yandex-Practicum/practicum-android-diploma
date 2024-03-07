package ru.practicum.android.diploma.filter.placeselector.country.domain.usecase

import ru.practicum.android.diploma.core.domain.model.Country
import ru.practicum.android.diploma.filter.domain.api.FilterRepository

class GetCountryFilterUseCase(
    private val filterRepository: FilterRepository
) {
    fun execute(): Country? {
        return filterRepository.getCountryFilterFromLocalStorage()
    }
}
