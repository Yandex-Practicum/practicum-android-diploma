package ru.practicum.android.diploma.area.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.area.domain.api.AreaInteractor
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.domain.repository.FiltersRepository

class AreaInteractorImpl(val repository: FiltersRepository) : AreaInteractor {
    override var country: Flow<Area?> = flow {
        repository.tempFilters.collect {
            emit(it.country)
        }
    }

    override var region: Flow<Area?> = flow {
        repository.tempFilters.collect {
            emit(it.region)
        }
    }

    override fun resetCountry() {
        repository.applyCountry(null)
    }

    override fun resetRegion() {
        repository.applyRegion(null)
    }
}
