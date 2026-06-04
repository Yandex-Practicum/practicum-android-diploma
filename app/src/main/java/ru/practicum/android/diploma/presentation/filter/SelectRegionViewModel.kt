package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactors.AreasInteractor
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.util.Resource

class SelectRegionViewModel(
    private val areasInteractor: AreasInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val countryId: String? = savedStateHandle.get<String>(WorkplaceFragment.COUNTRY_ID_KEY)

    private val _uiState = MutableStateFlow<SelectRegionUiState>(SelectRegionUiState.Loading)
    val uiState: StateFlow<SelectRegionUiState> = _uiState.asStateFlow()

    private var allRegions: List<Region> = emptyList()
    private var currentQuery: String = ""

    init {
        loadRegions()
    }

    fun loadRegions() {
        _uiState.value = SelectRegionUiState.Loading
        viewModelScope.launch {
            when (val result = areasInteractor.getRegions(countryId)) {
                is Resource.Success -> {
                    allRegions = result.data
                    applyFilter()
                }
                is Resource.Error -> {
                    _uiState.value = SelectRegionUiState.Error
                }
                Resource.Loading -> {
                    _uiState.value = SelectRegionUiState.Loading
                }
            }
        }
    }

    fun onQueryChanged(query: String) {
        currentQuery = query
        applyFilter()
    }

    fun onClearQueryClicked() {
        onQueryChanged("")
    }

    private fun applyFilter() {
        if (allRegions.isEmpty() && _uiState.value is SelectRegionUiState.Error) return

        val filtered = if (currentQuery.isBlank()) {
            allRegions
        } else {
            allRegions.filter {
                it.name.contains(currentQuery, ignoreCase = true)
            }
        }

        if (filtered.isEmpty() && currentQuery.isNotBlank()) {
            _uiState.value = SelectRegionUiState.EmptySearch(currentQuery)
        } else {
            _uiState.value = SelectRegionUiState.Content(regions = filtered, query = currentQuery)
        }
    }
}
