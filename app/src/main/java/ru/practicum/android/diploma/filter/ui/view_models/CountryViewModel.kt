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

class CountryViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    logger: Logger
) : BaseViewModel(logger) {

    var regionArgs: Region? = null
    var countryArgs: Country? = null
    private val _uiState: MutableStateFlow<FilterScreenState> =
        MutableStateFlow(FilterScreenState.Default)
    val uiState: StateFlow<FilterScreenState> = _uiState


    fun getCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor.getCountries().collect { state ->
                when (state) {
                    is NetworkResponse.Error -> {
                        log(this@CountryViewModel.thisName, "NetworkResponse.Error -> ${state.message}")
                        _uiState.value = FilterScreenState.Error(message = state.message)
                    }

                    is NetworkResponse.Offline -> {
                        log(this@CountryViewModel.thisName, "NetworkResponse.Offline -> ${state.message}")
                        _uiState.value = FilterScreenState.Error(message = state.message)
                    }

                    is NetworkResponse.Success -> {
                        log(this@CountryViewModel.thisName, "NetworkResponse.Success -> [${state.data.size}]")
                        _uiState.value = FilterScreenState.Content(state.data)
                    }

                    is NetworkResponse.NoData -> {
                        log(this@CountryViewModel.thisName, "NetworkResponse.NoData -> []")
                        _uiState.value = FilterScreenState.NoData(emptyList<Country>(), message = state.message)
                    }
                }
            }
        }
    }

    fun getRegions() {
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor.getRegions().collect { state ->
                when (state) {
                    is NetworkResponse.Error -> {
                        log(this@CountryViewModel.thisName, "NetworkResponse.Error -> ${state.message}")
                        _uiState.value = FilterScreenState.Error(message = state.message)
                    }

                    is NetworkResponse.Offline -> {
                        log(this@CountryViewModel.thisName, "NetworkResponse.Offline -> ${state.message}")
                        _uiState.value = FilterScreenState.Error(message = state.message)
                    }

                    is NetworkResponse.Success -> {
                        log(this@CountryViewModel.thisName, "NetworkResponse.Success -> [${state.data.size}]")
                        _uiState.value = FilterScreenState.Content(state.data)
                    }

                    is NetworkResponse.NoData -> {
                        log(this@CountryViewModel.thisName, "NetworkResponse.NoData -> []")
                        _uiState.value = FilterScreenState.NoData(emptyList<Region>(), message = state.message)
                    }
                }
            }
        }
    }

    private fun getCountry(key: String, defaultValue: Country) {

    }

    fun onSearchQueryChanged(text: String) {

    }
}