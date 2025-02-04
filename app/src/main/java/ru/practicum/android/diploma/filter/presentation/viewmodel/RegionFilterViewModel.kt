package ru.practicum.android.diploma.filter.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.sharedprefs.interactor.SharedPrefsInteractor
import ru.practicum.android.diploma.common.sharedprefs.models.City
import ru.practicum.android.diploma.common.sharedprefs.models.Filter
import ru.practicum.android.diploma.common.util.RegionConverter
import ru.practicum.android.diploma.common.util.debounce
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.filter.domain.model.Area
import ru.practicum.android.diploma.filter.domain.model.RegionViewState

class RegionFilterViewModel(
    private val filterInteractor: FilterInteractor,
    private val sharedPrefsInteractor: SharedPrefsInteractor,
) : ViewModel() {

    private val filter = sharedPrefsInteractor.getFilter()
    private var regionList = mutableListOf<Area>()
    private var job: Job? = null
    private var isSearchSuccessFull = false
    private val stateLiveData = MutableLiveData<RegionViewState>()
    fun observeState(): LiveData<RegionViewState> = stateLiveData
    private var lastSearchText: String? = null
    private val regionsSearchDebounce = debounce<String>(
        DELAY_2000,
        viewModelScope,
        true
    ) { changedText ->
        searchRegions(changedText)
    }

    fun searchDebounce(changedText: String) {
        if (lastSearchText != changedText) {
            lastSearchText = changedText
            regionsSearchDebounce(changedText)
        }
    }

    fun loadRegions() {
        renderState(RegionViewState.Loading)
        Log.d("RegionFilter", "$filter")
        when {
            filter.areaCountry == null -> getAllCisRegionList()
            filter.areaCountry.id.isNotEmpty() -> getRegionsByParentId(filter.areaCountry.id)
            else -> {}
        }
    }

    fun applyRegionToFilter(area: Area) {
        sharedPrefsInteractor.updateFilter(
            Filter(
                areaCity = City(
                    id = area.id,
                    name = area.name
                )
            )
        )
    }

    private fun getAllCisRegionList() {
        job?.cancel()
        job = viewModelScope.launch {
            filterInteractor.getAllRegions().collect { state ->
                if (state is RegionViewState.Success) {
                    isSearchSuccessFull = true
                    regionList = RegionConverter.mapAllCisRegions(state.areas).toMutableList()
                    renderState(RegionViewState.Success(regionList))
                } else {
                    isSearchSuccessFull = false
                    renderState(RegionViewState.ServerError)
                }
            }
        }
    }

    private fun getRegionsByParentId(parentId: String) {
        job?.cancel()
        job = viewModelScope.launch {
            filterInteractor.searchRegionsById(parentId).collect { state ->
                if (state is RegionViewState.Success) {
                    isSearchSuccessFull = true
                    regionList = RegionConverter.mapRegions(state.areas).toMutableList()
                    renderState(RegionViewState.Success(regionList))
                } else {
                    isSearchSuccessFull = false
                    renderState(RegionViewState.ServerError)
                }
            }
        }
    }

    fun searchRegions(query: String) {
        job?.cancel()
        if (isSearchSuccessFull) {
            job = viewModelScope.launch {
                renderState(RegionViewState.Loading)
                val lowerCaseQuery = query.lowercase()
                val filteredRegions = async(Dispatchers.Default) {
                    regionList.filter { region ->
                        region.name.lowercase().contains(lowerCaseQuery)
                    }
                }.await()
                if (filteredRegions.isNotEmpty()) {
                    renderState(RegionViewState.Success(filteredRegions))
                } else {
                    renderState(RegionViewState.NotFoundError)
                }
            }
        }
    }

    fun declineLastSearch() {
        job?.cancel()
        job = null
        renderState(RegionViewState.Success(regionList))
    }

    private fun renderState(state: RegionViewState) {
        stateLiveData.postValue(state)
    }

    companion object {
        private const val DELAY_2000 = 2000L
    }

    override fun onCleared() {
        job = null
        super.onCleared()
    }
}
