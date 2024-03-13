package ru.practicum.android.diploma.ui.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.domain.filter.FilterInfoRepository
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared
import ru.practicum.android.diploma.domain.region.RegionInteractor

class RegionViewModel(
    private val regionInteractor: RegionInteractor,
    private val filterInfoRepository: FilterInfoRepository
) : ViewModel() {

    private val stateLiveData = MutableLiveData<RegionState>()
    fun observeState(): LiveData<RegionState> = stateLiveData

    private val _countryState = MutableLiveData<CountryShared?>()
    val countryState: LiveData<CountryShared?> = _countryState

    init {
        viewModelScope.launch {
            // Получаем значение countryFlow из репозитория
            val country = filterInfoRepository.getCountryFlow().value
            _countryState.value = country
            // Передаем countryId в loadRegion()
            country?.let { country ->
                country.countryId?.let { countryId ->
                    loadRegion(countryId)
                }
            }
        }
    }

    fun loadRegion(regionId: String) {
        if (regionId.isEmpty()) {
            // Выбрать значение по умолчанию или выполнить другие действия
            renderState(RegionState.Empty)
            return
        }

        renderState(RegionState.Loading)
        viewModelScope.launch {
            regionInteractor.searchRegion(regionId)
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
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

    fun setRegionInfo(region: RegionShared) {
        filterInfoRepository.setRegionFlow(region)
    }
}
