package ru.practicum.android.diploma.area.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.area.domain.api.AreaInteractor
import ru.practicum.android.diploma.core.domain.models.Area

class AreaViewModelImpl(val interactor: AreaInteractor) : AreaViewModel() {
    private val _state = MutableStateFlow<AreaScreenState>(AreaScreenState())
    override var state: StateFlow<AreaScreenState> = _state.asStateFlow()
    init {
        viewModelScope.launch {
            interactor.country.collect {
                _state.value = _state.value.copy(country = it)
            }
            interactor.region.collect {
                _state.value = _state.value.copy(region = it)
            }
        }
    }
    override fun resetCountry() {
        _state.value = _state.value.copy(country = null)
    }

    override fun resetRegion() {
        _state.value = _state.value.copy(region = null)
    }

    override fun apply() {
        interactor.applyArea(_state.value.country, _state.value.region)
    }

    override fun onBack(country: Area?) {
        country?.let {
            _state.value = _state.value.copy(country = country)
        }
    }
}
