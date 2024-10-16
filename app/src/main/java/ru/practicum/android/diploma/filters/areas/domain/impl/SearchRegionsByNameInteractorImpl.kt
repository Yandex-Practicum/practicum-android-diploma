package ru.practicum.android.diploma.filters.areas.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.filters.areas.domain.api.SearchRegionsByNameInteractor
import ru.practicum.android.diploma.filters.areas.domain.api.SearchRegionsByNameRepository
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.network.HttpStatusCode

class SearchRegionsByNameInteractorImpl(private val repository: SearchRegionsByNameRepository) :
    SearchRegionsByNameInteractor {
    override fun setText(text: String) {
        repository.setText(text)
    }

    override fun setParentId(parentId: String) {
        repository.setParentId(parentId)
    }

    override fun searchRegionsByName(): Flow<Pair<List<Area>?, HttpStatusCode?>> {
        return repository.searchRegionsByName().map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, HttpStatusCode.OK)
                is Resource.Error -> Pair(null, result.httpStatusCode)
            }
        }
    }
}
