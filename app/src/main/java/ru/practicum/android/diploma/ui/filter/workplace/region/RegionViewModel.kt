package ru.practicum.android.diploma.ui.filter.workplace.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.converter.AreaConverter.mapToCountry
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.domain.filter.FilterRepositoryCountryFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositoryRegionFlow
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared
import ru.practicum.android.diploma.domain.region.RegionInteractor

class RegionViewModel(
    private val filterRepositoryCountryFlow: FilterRepositoryCountryFlow,
    private val filterRepositoryRegionFlow: FilterRepositoryRegionFlow,
    private val regionInteractor: RegionInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<RegionState>()
    fun observeState(): LiveData<RegionState> = stateLiveData

    private val _countryState = MutableLiveData<CountryShared?>()
    private var regions: List<Country> = listOf()

    init {
        viewModelScope.launch {
            // Получаем значение countryFlow из репозитория
            val country = filterRepositoryCountryFlow.getCountryFlow().value
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
            renderState(RegionState.Empty(REGION_CONTENT))
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

    fun search(text: String) {
        val filteredRegions = regions.filter { it.name?.lowercase()?.contains(text.lowercase()) == true }
        if (filteredRegions.isEmpty()) {
            renderState(
                RegionState.Empty(
                    message = R.string.no_such_region
                )
            )
        } else {
            renderState(
                RegionState.Content(filteredRegions)
            )
        }
    }

    // Поправить логику выполнения, вывод ошибок при загрузке
    private fun processResult(regionList: Country?, errorMessage: Int?) {
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
                    regions = regionList.areas
                        .map { it.mapToCountry() }
                        .sortedBy { it.name }
                    renderState(
                        RegionState.Content(regions)
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

    companion object {
        const val REGION_CONTENT = 200
    }
}
