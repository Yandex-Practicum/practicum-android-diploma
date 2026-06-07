package ru.practicum.android.diploma.domain.interactors.impl

import ru.practicum.android.diploma.domain.api.AreasRepository
import ru.practicum.android.diploma.domain.interactors.AreasInteractor
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.util.Resource

class AreasInteractorImpl(
    private val repository: AreasRepository
) : AreasInteractor {
    override suspend fun getCountries(): Resource<List<Country>> = repository.getCountries()
    override suspend fun getRegions(countryId: String?): Resource<List<Region>> = repository.getRegions(countryId)
}
