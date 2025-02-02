package ru.practicum.android.diploma.domain.filter.impl

import ru.practicum.android.diploma.data.filter.FilterParameters
import ru.practicum.android.diploma.domain.filter.api.FilterInteractor
import ru.practicum.android.diploma.domain.filter.api.FilterRepository

class FilterInteractorImpl(
    private val filterRepository: FilterRepository
): FilterInteractor {
    override fun saveToFilterStorage(filterParameters: FilterParameters) {
        filterRepository.saveToFilterStorage(filterParameters)
    }

    override fun readFromFilterStorage(): FilterParameters {
        return filterRepository.readFromFilterStorage()
    }

}
