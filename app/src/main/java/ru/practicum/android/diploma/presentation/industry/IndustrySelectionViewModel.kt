package ru.practicum.android.diploma.presentation.industry

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactors.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

class IndustrySelectionViewModel(
    private val interactor: IndustriesInteractor,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val preselectedId: Int =
        savedStateHandle.get<Int>(ARG_PRESELECTED_INDUSTRY_ID) ?: NO_ID

    private val _uiState = MutableStateFlow<IndustrySelectionUiState>(IndustrySelectionUiState.Loading)
    val uiState: StateFlow<IndustrySelectionUiState> = _uiState.asStateFlow()

    private val _selectedIndustry = MutableSharedFlow<Industry>(extraBufferCapacity = 1)
    val selectedIndustry: SharedFlow<Industry> = _selectedIndustry.asSharedFlow()

    init {
        loadIndustries()
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { state ->
            if (state is IndustrySelectionUiState.Content) {
                state.copy(
                    searchQuery = query,
                    filtered = if (query.isBlank()) {
                        state.industries
                    } else {
                        state.industries.filter { it.name.contains(query, ignoreCase = true) }
                    },
                )
            } else {
                state
            }
        }
    }

    fun onIndustryClicked(industry: Industry) {
        _uiState.update { state ->
            if (state is IndustrySelectionUiState.Content) {
                state.copy(selectedIndustry = industry)
            } else {
                state
            }
        }
    }

    fun onSelectClicked() {
        val current = _uiState.value
        if (current is IndustrySelectionUiState.Content) {
            current.selectedIndustry?.let { _selectedIndustry.tryEmit(it) }
        }
    }

    private fun loadIndustries() {
        viewModelScope.launch {
            _uiState.value = IndustrySelectionUiState.Loading
            when (val result = interactor.getIndustries()) {
                is Resource.Success -> {
                    val industries = result.data
                    val preselected = industries.find { it.id == preselectedId }
                    _uiState.value = IndustrySelectionUiState.Content(
                        industries = industries,
                        filtered = industries,
                        selectedIndustry = preselected,
                    )
                }
                is Resource.Error -> _uiState.value = if (result.code != null) {
                    IndustrySelectionUiState.ServerError
                } else {
                    IndustrySelectionUiState.NetworkError
                }
                else -> Unit
            }
        }
    }

    companion object {
        const val ARG_PRESELECTED_INDUSTRY_ID = "preselectedIndustryId"
        private const val NO_ID = -1
    }
}
