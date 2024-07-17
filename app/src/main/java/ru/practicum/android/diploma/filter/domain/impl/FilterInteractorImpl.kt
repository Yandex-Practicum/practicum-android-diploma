package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterStoreRepository
import ru.practicum.android.diploma.filter.domain.models.Filter

class FilterInteractorImpl(
    private val filterStoreRepository: FilterStoreRepository,
) : FilterInteractor {
    override fun loadFilter(): Filter = filterStoreRepository.load()
    override fun saveFilter(filter: Filter) = filterStoreRepository.save(filter)
}
