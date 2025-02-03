package ru.practicum.android.diploma.filter.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.CountryViewState
import ru.practicum.android.diploma.filter.domain.model.IndustryViewState
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository

class FilterInteractorImpl(
    private val filterRepository: FilterRepository
) : FilterInteractor {
    override fun getIndustries(): Flow<IndustryViewState> {
        return filterRepository.getIndustries()
    }

    override fun getCountries(): Flow<CountryViewState> {
        return filterRepository.getCountries()
    }
}
