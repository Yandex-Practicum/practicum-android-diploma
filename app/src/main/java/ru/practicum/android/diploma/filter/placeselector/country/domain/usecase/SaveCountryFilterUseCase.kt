package ru.practicum.android.diploma.filter.placeselector.country.domain.usecase

import ru.practicum.android.diploma.core.domain.model.Country
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.FilterType

class SaveCountryFilterUseCase(
    private val filterRepository: FilterRepository
) {
    fun execute(country: Country) {
        filterRepository.saveFilterToLocalStorage(
            FilterType.Country(
                id = country.id,
                name = country.name
            )
        )
    }
}
