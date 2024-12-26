package ru.practicum.android.diploma.domain.impl.region

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.region.RegionsInteractor
import ru.practicum.android.diploma.domain.api.region.RegionsRepository
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.ui.search.state.VacancyError
import ru.practicum.android.diploma.util.Resource

class RegionsInteractorImpl(private val regionsRepository: RegionsRepository) : RegionsInteractor {

    override fun getRegions(areaId: String?): Flow<Pair<List<Region>?, VacancyError?>> {
        return regionsRepository.getRegions(areaId).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}
