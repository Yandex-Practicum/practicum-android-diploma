package ru.practicum.android.diploma.area.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.area.domain.api.AreaInteractor
import ru.practicum.android.diploma.core.domain.models.Area

class AreaViewModelImpl(val interactor: AreaInteractor) : AreaViewModel() {
    private val _state = MutableStateFlow(AreaScreenState())
    override val state: StateFlow<AreaScreenState> = _state.asStateFlow()
    init {
        viewModelScope.launch {
            combine(interactor.country, interactor.region) { country, region -> country to region }
                .collect { (country, region) ->
                    _state.value = _state.value.copy(
                        country = country,
                        region = region,
                        regionCountryId = if (region != null) country?.id else null
                    )
                }
        }
    }
    override fun resetCountry() {
        _state.value = _state.value.copy(country = null)
    }

    override fun resetRegion() {
        _state.value = _state.value.copy(region = null, regionCountryId = null)
    }

    override fun apply() {
        interactor.applyArea(_state.value.country, _state.value.region)
    }

    override fun onCountrySelected(country: Area) {
        val current = _state.value
        val resetRegion = current.region != null && current.regionCountryId != country.id
        _state.value = current.copy(
            country = country,
            region = if (resetRegion) null else current.region,
            regionCountryId = if (resetRegion) null else current.regionCountryId
        )
    }

    override fun onRegionSelected(region: Area, regionCountry: Area) {
        val current = _state.value
        _state.value = current.copy(
            region = region,
            regionCountryId = regionCountry.id,
            country = current.country ?: regionCountry
        )
    }
}
