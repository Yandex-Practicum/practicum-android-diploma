package ru.practicum.android.diploma.domain.areas.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.areas.api.AreasInteractor
import ru.practicum.android.diploma.domain.areas.api.AreasRepository
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Places

class AreasInteractorImpl(
    private val areasRepository: AreasRepository
) : AreasInteractor {
    override fun getCountries(): Flow<Resource<List<Area>>> = flow {
        emit(areasRepository.getCountries())
    }

    override fun getRegionsWithCountries(countryId: String?): Flow<Resource<Places>> = flow {
        if (countryId != null) {
            emit(areasRepository.getAreasByCountry(countryId))
        } else {
            emit(areasRepository.getAllAreas())
        }
    }
}
