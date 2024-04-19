package ru.practicum.android.diploma.domain.filter

import kotlinx.coroutines.flow.SharedFlow
import ru.practicum.android.diploma.domain.models.Filters

interface FilterUpdateFlowRepository {
    fun getFlow(): SharedFlow<Filters>

    suspend fun emitFlow()
}
