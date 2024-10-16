package ru.practicum.android.diploma.filters.areas.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filters.areas.domain.api.FilterAreaInteractor
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.filters.areas.ui.presentation.RegionSelectScreenState
import ru.practicum.android.diploma.search.domain.api.RequestBuilderInteractor
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.network.HttpStatusCode

class RegionSelectViewModel(
    private val getRegionsInteractor: FilterAreaInteractor,
    private val requestBuilderInteractor: RequestBuilderInteractor
) : ViewModel() {

    private var latestSearchText: String? = null
    private val stateLiveData = MutableLiveData<RegionSelectScreenState>()

    fun observeState(): LiveData<RegionSelectScreenState> = stateLiveData

    fun getAllRegions() {
        viewModelScope.launch {
            getRegionsInteractor
                .getCountries()
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(foundAreas: List<Area>?, errorMessage: HttpStatusCode?) {
        when {
            errorMessage == HttpStatusCode.NOT_CONNECTED -> {
                renderState(RegionSelectScreenState.NetworkError)
            }

            foundAreas.isNullOrEmpty() -> {
                renderState(RegionSelectScreenState.Empty)
            }

            else -> {
                renderState(
                    RegionSelectScreenState.ChooseItem(
                        convertToRegions(foundAreas)
                    )
                )
            }
        }
    }

    private val regionSelectDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            stateLiveData.postValue(
                RegionSelectScreenState.FilterRequest(changedText)
            )
        }

    fun searchDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            regionSelectDebounce(changedText)
        }
    }

    private fun convertToRegions(foundAreas: List<Area>): List<Area> {
        val regions = mutableListOf<Area>()
        val foundCountries = foundAreas
            .sortedBy { if (it.id == "1001") 1 else 0 }

        foundCountries.forEach { area ->
            regions.addAll(area.areas)
        }
        return regions
    }

    private fun renderState(state: RegionSelectScreenState) {
        stateLiveData.postValue(state)
    }

    fun finishSelect(areaId: String) {
        requestBuilderInteractor.setArea(areaId)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 500L
    }
}
