package ru.practicum.android.diploma.presentation.filtration.region.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.AreaInteractor
import ru.practicum.android.diploma.domain.models.AreaResult
import ru.practicum.android.diploma.presentation.filtration.region.mapper.toRegionUiList
import ru.practicum.android.diploma.ui.filtration.region.fragment.ChooseRegionFragmentArgs
import ru.practicum.android.diploma.ui.filtration.region.model.RegionUi
import ru.practicum.android.diploma.ui.filtration.region.state.ChooseRegionUiState

class ChooseRegionViewModel(
    private val areaInteractor: AreaInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val countryId: Int = ChooseRegionFragmentArgs.fromSavedStateHandle(savedStateHandle).countryId

    private var allRegions: List<RegionUi> = emptyList()

    private val _state = MutableStateFlow(ChooseRegionUiState(isLoading = true))
    val state: StateFlow<ChooseRegionUiState> = _state.asStateFlow()

    init {
        loadRegions()
    }

    fun onSearchQueryChanged(query: String) {
        publishFilteredRegions(query)
    }

    fun onClearSearchClicked() {
        publishFilteredRegions(searchQuery = "")
    }

    private fun loadRegions() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isError = false,
                    isEmptySearchResult = false,
                )
            }
            when (val result = areaInteractor.getAreas()) {
                is AreaResult.Success -> {
                    allRegions = result.areas.toRegionUiList(countryId)
                    publishFilteredRegions(_state.value.searchQuery)
                }

                is AreaResult.NoInternet,
                is AreaResult.ServerError,
                is AreaResult.Empty,
                is AreaResult.Error,
                -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = true,
                            isEmptySearchResult = false,
                        )
                    }
                }
            }
        }
    }

    private fun publishFilteredRegions(searchQuery: String) {
        val trimmedQuery = searchQuery.trim()
        val filteredRegions = if (trimmedQuery.isEmpty()) {
            allRegions
        } else {
            allRegions.filter { region ->
                region.name.contains(trimmedQuery, ignoreCase = true)
            }
        }

        _state.update {
            it.copy(
                searchQuery = searchQuery,
                regions = filteredRegions,
                isLoading = false,
                isError = false,
                isEmptySearchResult = trimmedQuery.isNotEmpty() && filteredRegions.isEmpty(),
            )
        }
    }
}
