package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.filter.ui.models.FilterScreenState
import ru.practicum.android.diploma.root.BaseViewModel
import javax.inject.Inject

class RegionViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    logger: Logger
) : BaseViewModel(logger) {

    private val _uiState: MutableStateFlow<FilterScreenState> =
        MutableStateFlow(FilterScreenState.Default)
    val uiState: StateFlow<FilterScreenState> = _uiState

    init { getRegion() }

    private fun getRegion() {
        viewModelScope.launch(Dispatchers.IO) {
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
                        _uiState.value = FilterScreenState.NoData(emptyList())
                    }
                }
            }
        }
    }
}