package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.ui.models.CountryFilterScreenState
import ru.practicum.android.diploma.root.BaseViewModel
import javax.inject.Inject

class CountryViewModel @Inject constructor(
    val filterInteractor: FilterInteractor,
    logger: Logger
) : BaseViewModel(logger) {

    private val _uiState: MutableStateFlow<CountryFilterScreenState> =
        MutableStateFlow(CountryFilterScreenState.Default)
    val uiState: StateFlow<CountryFilterScreenState> = _uiState

    init {
        getCountries()
    }

    fun getCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor
                .getCountries()
                .collect { result ->
                    if (result.isEmpty())
                        _uiState.value = CountryFilterScreenState.Empty
                    else
                        _uiState.value = CountryFilterScreenState.Content(result)
                }
        }
    }


}