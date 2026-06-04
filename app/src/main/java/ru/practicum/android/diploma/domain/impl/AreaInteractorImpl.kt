package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.AreaInteractor
import ru.practicum.android.diploma.domain.api.AreaRepository
import ru.practicum.android.diploma.domain.models.AreaResult

class AreaInteractorImpl(
    private val areaRepository: AreaRepository,
) : AreaInteractor {
    override suspend fun getAreas(): AreaResult {
        return areaRepository.getAreas()
    }
}
