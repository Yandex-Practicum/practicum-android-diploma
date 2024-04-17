package ru.practicum.android.diploma.ui.filter.workplace.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.converter.AreaConverter.mapToCountry
import ru.practicum.android.diploma.data.vacancies.response.ResponseCodeConstants
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.domain.country.CountryInteractor
import ru.practicum.android.diploma.domain.country.CountryRepositoryFlow
import ru.practicum.android.diploma.domain.debugLog
import ru.practicum.android.diploma.domain.filter.FilterRepositoryCountryFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositoryRegionFlow
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared
import ru.practicum.android.diploma.domain.region.RegionInteractor

class RegionViewModel(
    private val filterRepositoryCountryFlow: FilterRepositoryCountryFlow,
    private val filterRepositoryRegionFlow: FilterRepositoryRegionFlow,
    private val countryRepositoryFlow: CountryRepositoryFlow,
    private val regionInteractor: RegionInteractor,
    private val countryInteractor: CountryInteractor,
) : ViewModel() {

    private val stateLiveDataRegion = MutableLiveData<RegionState>()
    fun observeStateRegion(): LiveData<RegionState> = stateLiveDataRegion

    private val _countryState = MutableLiveData<CountryShared?>()
    private var regions: List<Country> = listOf()

    init {
        viewModelScope.launch {
            // Получаем значение countryFlow из репозитория
            val country = filterRepositoryCountryFlow.getCountryFlow().value
            _countryState.value = country
            // Передаем countryId в loadRegion()
            if (country != null && !country.countryId.isNullOrEmpty()) {
                country.let { country ->
                    country.countryId?.let { countryId ->
                        loadRegion(countryId)
                        countryInteractor.searchCountry()
                    }
                }
            } else {
                loadCountry()
            }
        }
    }

    private fun loadCountry() {
        val regionAll = mutableListOf<Country>()
        renderState(RegionState.Loading)
        viewModelScope.launch {
            countryInteractor.searchCountry()
                .collect { pair ->
                    pair.first?.forEach { country ->
                        if (!country.id.contains(OTHER_REGIONS_ID)) {
                            regionInteractor.searchRegion(country.id)
                                .collect { region ->
                                    regionAll.addAll(region.first!!.areas.map { it.mapToCountry() })
                                    debugLog(TAG) { "loadCountry: region = ${regionAll.map { it.name }}" }
                                }
                        }
                    }
                }
            renderState(RegionState.Content(regionAll.sortedBy { it.name }))
            debugLog(TAG) { "loadCountry: в конце корутины regionAll = ${regionAll.map { it.name }}" }
        }
        debugLog(TAG) { "loadCountry: после корутины regionAll = ${regionAll.map { it.name }}" }
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
                if (errorMessage == -1) {
                    renderState(RegionState.Error.NO_CONNECTION)
                } else if (errorMessage == ResponseCodeConstants.SERVER_ERROR) {
                    renderState(RegionState.Error.SERVER_ERROR)
                } else {
                    renderState(RegionState.Error.NOTHING_FOUND)
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
        stateLiveDataRegion.postValue(regionState)
    }

    fun setRegionInfo(region: RegionShared) {
        filterRepositoryRegionFlow.setRegionFlow(region)
    }

    fun setCountryInfo(country: CountryShared) {
        filterRepositoryCountryFlow.setCountryFlow(country)
    }

    fun getCountry(parentId: String?) {
        val countryAll = countryRepositoryFlow.getCountryFlow().value
        debugLog(TAG) { "getCountry: countryAll = ${countryAll.map { it.value }}" }
        countryAll.forEach { country ->
            setCountryInfo(
                CountryShared(
                    countryId = parentId,
                    countryName = countryAll[parentId?.toInt()]
                )
            )
        }
    }

    companion object {
        const val TAG = "RegionViewModel"
        const val OTHER_REGIONS_ID = "1001"
    }
}
