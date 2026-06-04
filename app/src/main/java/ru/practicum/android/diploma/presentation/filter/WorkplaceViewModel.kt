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
        _uiState.update {
            WorkplaceUiState(
                countryId = settings.countryId?.toString(),
                countryName = settings.countryName,
                regionId = settings.regionId?.toString(),
                regionName = settings.regionName
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
        _uiState.update {
            WorkplaceUiState(
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
