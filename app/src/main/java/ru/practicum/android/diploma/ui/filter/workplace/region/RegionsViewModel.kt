package ru.practicum.android.diploma.ui.filter.workplace.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.region.RegionsInteractor
import ru.practicum.android.diploma.domain.models.Region

class RegionsViewModel(private val regionsInteractor: RegionsInteractor) : ViewModel() {

    private val _state = MutableLiveData<RegionsState>()
    val state: LiveData<RegionsState> = _state
    private var regions: List<Region>? = null

    fun getRegions(areaId: String?) {
        _state.postValue(RegionsState.Loading)
        viewModelScope.launch {
            regionsInteractor.getRegions(areaId).collect {
                processResult(it.first, it.second.toString())
            }
        }
    }

    private fun processResult(data: List<Region>?, message: String?) {
        if (data != null) {
            _state.postValue(RegionsState.Content(data))
            regions = data
        } else {
            _state.postValue(RegionsState.Error(message.toString()))
        }
    }

    fun getRegionsList() = regions
}
