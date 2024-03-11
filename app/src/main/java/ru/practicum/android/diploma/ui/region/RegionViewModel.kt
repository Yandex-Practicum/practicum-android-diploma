package ru.practicum.android.diploma.ui.region

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.domain.region.RegionInteractor

class RegionViewModel(
    val regionInteractor: RegionInteractor,
) : ViewModel() {

    private val stateLiveData = MutableLiveData<RegionState>()
    fun observeState(): LiveData<RegionState> = stateLiveData

    fun loadRegion(regionId: String) {
        renderState(RegionState.Loading)
        Log.d("RegionState", "Прокидываем во ViewModel 1 ID = $regionId")
        viewModelScope.launch {
            regionInteractor.searchRegion(regionId)
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
            Log.d("RegionState", "Прокидываем во ViewModel 2 ID = $regionId")
        }
    }

    private fun processResult(industriesDetail: Country?, errorMessage: Int?) {
        when {
            errorMessage != null -> {
                renderState(
                    RegionState.Error(
                        errorMessage = R.string.server_error
                    )
                )
            }

            else -> {
                renderState(
                    RegionState.Content(
                        regionId = industriesDetail!!
                    )
                )
            }
        }
    }

    fun renderState(regionState: RegionState) {
        stateLiveData.postValue(regionState)
    }
}
