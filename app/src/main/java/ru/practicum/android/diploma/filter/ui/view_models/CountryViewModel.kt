package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse.*
import ru.practicum.android.diploma.filter.ui.fragments.CountryFilterFragment.Companion.COUNTRY
import ru.practicum.android.diploma.filter.ui.models.FilterScreenState
import ru.practicum.android.diploma.filter.ui.view_models.BaseFilterViewModel.Companion.FILTER_KEY
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

open class CountryViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    logger: Logger
): BaseViewModel(logger)  {

    protected val _uiState: MutableStateFlow<FilterScreenState> =
        MutableStateFlow(FilterScreenState.Default)
    val uiState: StateFlow<FilterScreenState> = _uiState


    open fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor.getCountries().collect { state ->
                log("CountryViewModel", "getCountries().collect { state -> ${state.thisName}")
                _uiState.value = when (state) {
                    is Error -> FilterScreenState.Error(message = state.message)
                    is Offline -> FilterScreenState.Error(message = state.message)
                    is Success -> FilterScreenState.Content(state.data)
                    is NoData -> FilterScreenState.NoData(emptyList<Country>(), message = state.message)
                }
            }
        }
    }

    fun saveCountry(country: Country) {
        log(thisName, "saveCountry(country: Country)")
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor.saveCountry(FILTER_KEY, country)
        }
    }

    fun onSearchQueryChanged(text: String) { /* ignore */ }
}