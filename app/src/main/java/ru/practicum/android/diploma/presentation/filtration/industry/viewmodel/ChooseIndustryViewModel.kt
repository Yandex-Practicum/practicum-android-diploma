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
import ru.practicum.android.diploma.presentation.filtration.industry.state.ChooseIndustryUiState
import ru.practicum.android.diploma.presentation.filtration.industry.state.IndustryUiState

class ChooseIndustryViewModel(
    private val industryInteractor: IndustryInteractor,
) : ViewModel() {
    private var allIndustries: List<FilterIndustry> = emptyList()

    private val _state = MutableStateFlow(ChooseIndustryUiState())
    val state: StateFlow<ChooseIndustryUiState> = _state.asStateFlow()

    init {
        loadIndustries()
    }

    fun onSearchQueryChanged(query: String) {
        publishState(searchQuery = query)
    }

    fun clearSearch() {
        publishState(searchQuery = "")
    }

    fun onIndustryClick(item: FilterIndustry) {
        publishState(selectedIndustry = item)
    }

    private fun loadIndustries() {
        viewModelScope.launch {
            publishState(status = IndustryUiState.Content(isLoading = true))
            when (val outcome = industryInteractor.getIndustries()) {
                is IndustryResult.Success -> {
                    allIndustries = outcome.industries
                    publishState(status = IndustryUiState.Content(isLoading = false))
                }
                is IndustryResult.Error,
                is IndustryResult.NoInternet,
                is IndustryResult.ServerError,
                -> publishState(status = IndustryUiState.Error)
            }
        }
    }

    private fun publishState(
        status: IndustryUiState = _state.value.status,
        searchQuery: String = _state.value.searchQuery,
        selectedIndustry: FilterIndustry? = _state.value.selectedIndustry,
    ) {
        _state.value = ChooseIndustryUiState(
            status = status,
            searchQuery = searchQuery,
            industries = filterIndustries(searchQuery),
            selectedIndustry = selectedIndustry,
        )
    }

    private fun filterIndustries(query: String): List<FilterIndustry> {
        val trimmedQuery = query.trim()
        if (trimmedQuery.isEmpty()) {
            return allIndustries
        }
        return allIndustries.filter { industry ->
            industry.name.contains(trimmedQuery, ignoreCase = true)
        }
    }
}
