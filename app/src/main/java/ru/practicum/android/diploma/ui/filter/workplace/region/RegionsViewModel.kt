package ru.practicum.android.diploma.ui.filter.workplace.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.region.RegionsInteractor
import ru.practicum.android.diploma.domain.models.Region

class RegionsViewModel(private val regionsInteractor: RegionsInteractor) : ViewModel() {

    private val _state = MutableLiveData<RegionsState>()
    val state: LiveData<RegionsState> = _state
    private var regions: List<Region>? = null
    private var currentSearchQuery: String = ""
    private var searchJob: Job? = null
    private var isNetworkError: Boolean = false

    fun getRegions(areaId: String?) {
        _state.postValue(RegionsState.Loading)
        viewModelScope.launch {
            regionsInteractor.getRegions(areaId).collect {
                processResult(it.first)
            }
        }
    }

    private fun processResult(data: List<Region>?) {
        if (data != null) {
            if (data.isEmpty()) {
                _state.postValue(RegionsState.NotFound)
            } else {
                _state.postValue(RegionsState.Content(data))
                regions = data
            }
        } else {
            _state.postValue(RegionsState.Error)
            isNetworkError = true
        }
    }

    fun searchRegions(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            if (query.isNotBlank() && query != currentSearchQuery) {
                currentSearchQuery = query
                if (isNetworkError) {
                    _state.postValue(RegionsState.Error)
                } else {
                    val filteredRegions = regions?.filter { region ->
                        region.name?.lowercase()?.contains(query.lowercase()) ?: false
                    }
                    if (filteredRegions.isNullOrEmpty()) {
                        _state.postValue(RegionsState.NotFound)
                    } else {
                        _state.postValue(RegionsState.Content(filteredRegions))
                    }
                }
            }
        }
    }

    fun resetSearch() {
        currentSearchQuery = ""
        regions?.let {
            _state.postValue(RegionsState.Content(it))
        }
    }
}
