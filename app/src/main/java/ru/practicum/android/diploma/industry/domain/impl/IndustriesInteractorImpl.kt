package ru.practicum.android.diploma.industry.domain.impl

import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.Industry
import ru.practicum.android.diploma.core.domain.repository.FiltersRepository
import ru.practicum.android.diploma.industry.domain.api.IndustriesInteractor
import ru.practicum.android.diploma.industry.domain.api.IndustriesRepository

class IndustriesInteractorImpl(
    val industriesRepository: IndustriesRepository,
    val filtersRepository: FiltersRepository
) : IndustriesInteractor {
    override suspend fun getIndustries(query: String): Resource<List<Industry>> {
        return industriesRepository.getIndustries(query)
    }

    override fun applyFilter(industry: Industry?) {
        filtersRepository.applyIndustry(industry)
    }
}
