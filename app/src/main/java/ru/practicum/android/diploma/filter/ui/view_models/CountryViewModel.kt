package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.ui.models.FilterScreenState
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

open class CountryViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    logger: Logger
) : BaseViewModel(logger) {

    var countryArgs: Country? = null
    protected val _uiState: MutableStateFlow<FilterScreenState> =
        MutableStateFlow(FilterScreenState.Default)
    val uiState: StateFlow<FilterScreenState> = _uiState


    protected open fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            log("CountryViewModel", "filterInteractor.getCountries()")
            filterInteractor.getCountries().collect { state ->
                when (state) {
                    is NetworkResponse.Error -> {
                        log("CountryViewModel", "NetworkResponse.Error -> ${state.message}")
                        _uiState.value = FilterScreenState.Error(message = state.message)
                    }

                    is NetworkResponse.Offline -> {
                        log("CountryViewModel", "NetworkResponse.Offline -> ${state.message}")
                        _uiState.value = FilterScreenState.Error(message = state.message)
                    }

                    is NetworkResponse.Success -> {
                        log("CountryViewModel", "NetworkResponse.Success -> [${state.data.size}]")
                        _uiState.value = FilterScreenState.Content(state.data)
                    }

                    is NetworkResponse.NoData -> {
                        log("CountryViewModel", "NetworkResponse.NoData -> []")
                        _uiState.value = FilterScreenState.NoData(emptyList<Country>(), message = state.message)
                    }
                }
            }
        }
    }

    open fun hasUserData(fragment: String) {
        if (fragment == COUNTRY_KEY) {
            getData()
        }
    }

    fun saveCountry(country: Country) {
        log(thisName, "saveCountry(country: Country)")
        viewModelScope.launch {
            filterInteractor.saveCountry(COUNTRY_KEY, country)
        }
    }

    fun onSearchQueryChanged(text: String) {

    }

    companion object { const val COUNTRY_KEY = "Country" }

}