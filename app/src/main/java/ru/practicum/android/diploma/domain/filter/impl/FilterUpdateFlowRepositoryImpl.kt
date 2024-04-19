package ru.practicum.android.diploma.domain.filter.impl

import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import ru.practicum.android.diploma.domain.filter.FilterUpdateFlowRepository
import ru.practicum.android.diploma.domain.filter.FiltersRepository
import ru.practicum.android.diploma.domain.models.Filters

class FilterUpdateFlowRepositoryImpl(private val filtersRepository: FiltersRepository) : FilterUpdateFlowRepository {

    private val flow = MutableSharedFlow<Filters>()

    override fun getFlow(): SharedFlow<Filters> = flow
    override suspend fun emitFlow() {
        filtersRepository.getFiltersFlow().collect {
            Log.e("emit", it.toString())
            flow.emit(it)
        }
    }
}
