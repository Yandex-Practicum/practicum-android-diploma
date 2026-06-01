package ru.practicum.android.diploma.area.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.area.domain.api.AreaInteractor

class AreaViewModelImpl(val interactor: AreaInteractor) : AreaViewModel() {
    private val _state = MutableStateFlow<AreaScreenState>(AreaScreenState())
    override var state: StateFlow<AreaScreenState> = _state.asStateFlow()
    init {
        viewModelScope.launch {
            interactor.country.collect {
                _state.value = _state.value.copy(country = it?.name)
            }
            interactor.region.collect {
                _state.value = _state.value.copy(region = it?.name)
            }
        }
    }
    override fun resetCountry() {
        interactor.resetCountry()
    }

    override fun resetRegion() {
        interactor.resetRegion()
    }
}
