package ru.practicum.android.diploma.filters.domain.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.filters.domain.api.FilterAreaInteractor
import ru.practicum.android.diploma.filters.domain.api.FilterAreaRepository
import ru.practicum.android.diploma.filters.domain.models.Area
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.network.HttpStatusCode

class FilterAreaInteractorImpl(
    private val repository: FilterAreaRepository
) : FilterAreaInteractor {

    override fun getCountries(): Flow<Pair<List<Area>?, HttpStatusCode?>> {
        return repository.getAllRegions().map { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d("T", "ZAEBISKA ${result.data}")
                    Pair(result.data, HttpStatusCode.OK)
                }
                is Resource.Error -> {
                    Log.e("T", "Error loading countries: ${result.httpStatusCode}")
                    Pair(null, result.httpStatusCode)
                }
            }
        }
    }
}
