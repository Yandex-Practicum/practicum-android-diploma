package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse.*
import ru.practicum.android.diploma.filter.ui.models.AreasUiState
import ru.practicum.android.diploma.filter.ui.models.FilterScreenState
import ru.practicum.android.diploma.util.thisName

abstract class AreasViewModel(
    private val logger: Logger,
    private val filterInteractor: FilterInteractor,
) : ViewModel() {


    protected val _uiState: MutableStateFlow<AreasUiState> =
        MutableStateFlow(AreasUiState.Loading)
    val uiState: StateFlow<AreasUiState> = _uiState

    open fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor.getCountries().collect { state ->
                log("CountryViewModel", "getCountries().collect { state -> ${state.thisName}")
                _uiState.value = when (state) {
                    is Success -> AreasUiState.Content(state.data)
                    is NoData  -> AreasUiState.NoData(message = state.message)
                    is Offline -> AreasUiState.Offline(message = state.message)
                    is Error   -> AreasUiState.Error(message = state.message)
                }
            }
        }
    }

    open fun onSearchQueryChanged(text: String) { /* ignore */ }

    fun log(name: String, text: String) {
        logger.log(name, text)
    }
}