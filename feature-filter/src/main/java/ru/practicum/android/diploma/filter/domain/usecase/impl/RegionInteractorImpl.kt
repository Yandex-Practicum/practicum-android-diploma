package ru.practicum.android.diploma.filter.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.filter.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.domain.repository.RegionRepository
import ru.practicum.android.diploma.filter.domain.usecase.RegionInteractor

internal class RegionInteractorImpl(private val repository: RegionRepository) : RegionInteractor {
    override fun listAreas(): Flow<Pair<List<AreaInReference>?, String?>> {
        return repository.listAreas().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, "")
                }

                is Resource.Error -> {
                    Pair(null, result.message ?: "")
                }
            }
        }
    }
}
