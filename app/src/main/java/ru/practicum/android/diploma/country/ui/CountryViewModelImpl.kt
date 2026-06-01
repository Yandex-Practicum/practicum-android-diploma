package ru.practicum.android.diploma.country.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.data.network.ResultCode
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.country.domain.api.CountryInteractor

class CountryViewModelImpl(
    val interactor: CountryInteractor
) : CountryViewModel() {
    private val _state = MutableStateFlow<CountryScreenState>(CountryScreenState.Loading)

    override var state: StateFlow<CountryScreenState> = _state.asStateFlow()
    init {
        viewModelScope.launch {
            interactor.getCountries().collect { response ->
                when (response) {
                    is Resource.Loading -> CountryScreenState.Loading
                    is Resource.Success ->
                        _state.value = if (response.data.isEmpty()) {
                            CountryScreenState.Error(CountryError.ERROR)
                        } else {
                            CountryScreenState.Content(response.data)
                        }

                    is Resource.Error ->
                        _state.value = CountryScreenState.Error(
                            if (response.code == ResultCode.NO_INTERNET) {
                                CountryError.NO_INTERNET
                            } else {
                                CountryError.ERROR
                            }
                        )
                }
            }
        }
    }
    override fun selectCountry(country: Area) {
        interactor.selectCountry(country)
    }

}
