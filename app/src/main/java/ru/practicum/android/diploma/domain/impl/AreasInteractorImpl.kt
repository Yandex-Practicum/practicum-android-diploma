package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.filters.AreasInteractor
import ru.practicum.android.diploma.domain.filters.AreasRepository
import ru.practicum.android.diploma.domain.models.Areas
import ru.practicum.android.diploma.util.Resource

class AreasInteractorImpl(
    val repository: AreasRepository
) : AreasInteractor {
    var cacheAreas: List<Areas>? = null

    override fun getAreas(): Flow<Pair<List<Areas>?, Int?>> {
        return if (cacheAreas == null) {
            repository.getAreas().map { result ->
                when (result) {
                    is Resource.Success -> {
                        cacheAreas = result.data
                        Pair(result.data, null)
                    }

                    is Resource.Error -> Pair(null, result.error)
                }
            }
        } else {
            flow { Pair(first = cacheAreas, second = null) }
        }
    }
}
