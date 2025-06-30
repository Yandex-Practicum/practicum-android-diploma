package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.filters.AreasInteractor
import ru.practicum.android.diploma.domain.filters.AreasRepository
import ru.practicum.android.diploma.domain.models.Areas
import ru.practicum.android.diploma.util.Resource

class AreasInteractorImpl(
    val repository: AreasRepository
) : AreasInteractor {
    override fun getAreas(): Flow<Pair<List<Areas>?, Int?>> {
        return repository.getAreas().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> Pair(null, result.error)
            }
        }
    }
}
