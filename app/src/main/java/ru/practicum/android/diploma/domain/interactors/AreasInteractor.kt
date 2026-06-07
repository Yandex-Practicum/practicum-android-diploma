package ru.practicum.android.diploma.domain.interactors

import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.util.Resource

interface AreasInteractor {
    suspend fun getCountries(): Resource<List<Country>>
    suspend fun getRegions(countryId: String?): Resource<List<Region>>
}
