package ru.practicum.android.diploma.filter.ui.view_models

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.ui.models.CountryFilterScreenState
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.search.data.network.dto.CountryDto
import ru.practicum.android.diploma.search.ui.models.SearchScreenState
import javax.inject.Inject

class CountryViewModel @Inject constructor(
    val filterInteractor: FilterInteractor,
    logger: Logger
) : BaseViewModel(logger) {

    private var countriesList = ArrayList<Country>()
    private val _uiState: MutableStateFlow<CountryFilterScreenState> =
        MutableStateFlow(CountryFilterScreenState.Empty)
    val uiState: StateFlow<CountryFilterScreenState> = _uiState



     fun getCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor
                .getCountries()
                .collect { result ->
                    _uiState.value =  CountryFilterScreenState.Content(result)
                }
        }
    }


}