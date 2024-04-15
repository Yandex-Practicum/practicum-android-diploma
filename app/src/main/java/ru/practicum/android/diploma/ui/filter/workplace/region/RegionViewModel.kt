package ru.practicum.android.diploma.ui.filter.workplace.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.domain.country.CountryInteractor
import ru.practicum.android.diploma.domain.debugLog
import ru.practicum.android.diploma.domain.filter.FilterRepositoryCountryFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositoryRegionFlow
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared
import ru.practicum.android.diploma.domain.region.RegionInteractor

class RegionViewModel(
    private val filterRepositoryCountryFlow: FilterRepositoryCountryFlow,
    private val filterRepositoryRegionFlow: FilterRepositoryRegionFlow,
    private val regionInteractor: RegionInteractor,
    private val countryInteractor: CountryInteractor,
) : ViewModel() {

    private val stateLiveData = MutableLiveData<RegionState>()
    fun observeState(): LiveData<RegionState> = stateLiveData

    private val _countryState = MutableLiveData<CountryShared?>()

    init {
        viewModelScope.launch {
            // Получаем значение countryFlow из репозитория
            val country = filterRepositoryCountryFlow.getCountryFlow().value
            _countryState.value = country
            // Передаем countryId в loadRegion()
            if (country != null && !country.countryId.isNullOrEmpty()) {
                country?.let { country ->
                    country.countryId?.let { countryId ->
                        loadRegion(countryId)
                        countryInteractor.searchCountry()
                    }
                }
            } else {
                debugLog(TAG) { "init: country = $country и state = ${_countryState.value?.countryId}" }
                loadCountry()
            }

        }
    }

    private fun loadCountry() {
        viewModelScope.launch {
            countryInteractor.searchCountry()
                .collect { pair ->
                    pair.first?.forEach { country ->
                        debugLog(TAG) { "loadCountry: country = $country" }
                        regionInteractor.searchRegion(country.id)
                            .collect { region ->
                                // Тут стоит добавлять в новый список
                                debugLog(TAG) { "loadCountry: region = $region" }
                                processResult(region.first, pair.second)
                            }
                    }
                }
        }
        debugLog(TAG) { "loadCountry: countryAllRegion = $" }
    }

    private fun loadRegion(regionId: String) {
        renderState(RegionState.Loading)
        viewModelScope.launch {
            regionInteractor.searchRegion(regionId)
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    // Поправить логику выполнения, вывод ошибок при загрузке
    private fun processResult(regionList: Country?, errorMessage: Int?) {
        debugLog(TAG) { "processResult: region = $regionList" }
        when {
            errorMessage != null -> {
                renderState(
                    RegionState.Error(
                        errorMessage = R.string.server_error
                    )
                )

                if (errorMessage == -1) {
                    renderState(
                        RegionState.Error(
                            errorMessage = R.string.nothing_found
                        )
                    )
                } else {
                    renderState(
                        RegionState.Empty(
                            message = R.string.no_such_region
                        )
                    )
                }

            }

            else -> {
                if (regionList != null) {
                    renderState(
                        RegionState.Content(
                            regionId = regionList
                        )
                    )
                } else {
                    renderState(
                        RegionState.Empty(
                            message = R.string.no_such_region
                        )
                    )
                }
            }
        }
    }

    private fun renderState(regionState: RegionState) {
        stateLiveData.postValue(regionState)
    }

    fun setRegionInfo(region: RegionShared) {
        filterRepositoryRegionFlow.setRegionFlow(region)
    }

    fun setCountryInfo(country: CountryShared) {
        filterRepositoryCountryFlow.setCountryFlow(country)
    }

    companion object {
        const val TAG = "RegionViewModel"
    }
}
