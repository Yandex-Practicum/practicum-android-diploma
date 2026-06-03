package ru.practicum.android.diploma.presentation.filtration.industry.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.IndustryInteractor
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.domain.models.IndustryResult
import ru.practicum.android.diploma.presentation.filtration.industry.state.IndustryUiState

class ChooseIndustryViewModel(
   private val industryInteractor: IndustryInteractor
) : ViewModel() {
    private val _state = MutableStateFlow<IndustryUiState>(IndustryUiState.Initial)
    val state: StateFlow<IndustryUiState> = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow(IndustryScreenState())
    val uiState: StateFlow<IndustryScreenState> = _uiState.asStateFlow()

    init {
        loadIndustries()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            resetSearchState()
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        resetSearchState()
    }

    fun onIndustryClick(item: FilterIndustry) {

    }

    private fun loadIndustries() {
        viewModelScope.launch {
            _state.value = IndustryUiState.Initial
            when (val outcome = industryInteractor.getIndustries()) {
                is IndustryResult.Success -> _state.value = IndustryUiState.Content(
                    industries = outcome.industries,
                    isLoading = false,
                )
                is IndustryResult.Error -> _state.value = IndustryUiState.Error
                is IndustryResult.NoInternet -> _state.value = IndustryUiState.Error
                is IndustryResult.ServerError -> _state.value = IndustryUiState.Error
            }
        }
    }

    private fun resetSearchState() {
        _state.value = IndustryUiState.Initial
    }

    data class IndustryScreenState(
        val industryList: List<FilterIndustry> = emptyList(),
        val selectedIndustry: FilterIndustry? = null,
        val errorVisible: Boolean = false,
        val recyclerVisible: Boolean = false,
        val progressBarVisible: Boolean = false,
        val errorText: String = "",
        val errorIcon: Int = 0,
        val showButton: Boolean = false,
    )
}
