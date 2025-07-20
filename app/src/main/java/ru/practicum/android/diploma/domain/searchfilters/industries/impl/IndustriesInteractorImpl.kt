package ru.practicum.android.diploma.domain.searchfilters.industries.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.industries.Industry
import ru.practicum.android.diploma.domain.searchfilters.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.searchfilters.industries.IndustriesRepository
import ru.practicum.android.diploma.util.Resource

class IndustriesInteractorImpl(private val industriesRepository: IndustriesRepository) : IndustriesInteractor {
    override fun getIndustries(): Flow<Resource<List<Industry>>> {
        return industriesRepository.getIndustries()
    }
}
