package ru.practicum.android.diploma.area.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.models.Area

interface AreaInteractor {
    var country: Flow<Area?>
    var region: Flow<Area?>
    fun applyArea(country: Area?, region: Area?)
}
