package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.practicum.android.diploma.domain.interactors.FilterInteractor

class WorkplaceViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkplaceUiState())
    val uiState: StateFlow<WorkplaceUiState> = _uiState.asStateFlow()

    init {
        loadFilterSettings()
    }

    fun loadFilterSettings() {
        val settings = filterInteractor.getFilterSettings()
        val cId = settings.countryId?.toString()
        val cName = settings.countryName
        val rId = settings.regionId?.toString()
        val rName = settings.regionName
        _uiState.update {
            WorkplaceUiState(
                countryId = cId,
                countryName = cName,
                regionId = rId,
                regionName = rName,
                initialCountryId = cId,
                initialCountryName = cName,
                initialRegionId = rId,
                initialRegionName = rName
            )
        }
    }

    fun onCountrySelected(id: String, name: String) {
        _uiState.update { state ->
            if (state.countryId != id) {
                state.copy(
                    countryId = id,
                    countryName = name,
                    regionId = null,
                    regionName = null
                )
            } else {
                state
            }
        }
    }

    fun onRegionSelected(regionId: String, regionName: String, countryId: String, countryName: String) {
        _uiState.update { state ->
            state.copy(
                countryId = countryId,
                countryName = countryName,
                regionId = regionId,
                regionName = regionName
            )
        }
    }

    fun onCountryCleared() {
        _uiState.update {
            it.copy(
                countryId = null,
                countryName = null,
                regionId = null,
                regionName = null
            )
        }
    }

    fun onRegionCleared() {
        _uiState.update {
            it.copy(
                regionId = null,
                regionName = null
            )
        }
    }

    fun onApplyClicked() {
        val state = _uiState.value
        val currentSettings = filterInteractor.getFilterSettings()
        val newSettings = currentSettings.copy(
            countryId = state.countryId?.toIntOrNull(),
            countryName = state.countryName,
            regionId = state.regionId?.toIntOrNull(),
            regionName = state.regionName
        )
        filterInteractor.saveFilterSettings(newSettings)
    }
}
