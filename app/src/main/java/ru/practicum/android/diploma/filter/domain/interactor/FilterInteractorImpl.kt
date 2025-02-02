package ru.practicum.android.diploma.filter.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.filter.domain.model.IndustryViewState
import ru.practicum.android.diploma.filter.domain.model.Region
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository

class FilterInteractorImpl(
    private val filterRepository: FilterRepository
) : FilterInteractor {
    override fun getIndustries(): Flow<IndustryViewState> {
        return filterRepository.getIndustries()
    }

    override fun getCountry(): Country?{
        return filterRepository.getCountry()
    }

    override fun getRegion(): Region?{
        return filterRepository.getRegion()
    }
}
