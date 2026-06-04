package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactors.AreasInteractor
import ru.practicum.android.diploma.util.Resource

class SelectCountryViewModel(
    private val areasInteractor: AreasInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow<SelectCountryUiState>(SelectCountryUiState.Loading)
    val uiState: StateFlow<SelectCountryUiState> = _uiState.asStateFlow()

    init {
        loadCountries()
    }

    fun loadCountries() {
        _uiState.value = SelectCountryUiState.Loading
        viewModelScope.launch {
            when (val result = areasInteractor.getCountries()) {
                is Resource.Success -> {
                    _uiState.value = SelectCountryUiState.Content(result.data)
                }
                is Resource.Error -> {
                    _uiState.value = SelectCountryUiState.Error
                }
                Resource.Loading -> {
                    _uiState.value = SelectCountryUiState.Loading
                }
            }
        }
    }
}
