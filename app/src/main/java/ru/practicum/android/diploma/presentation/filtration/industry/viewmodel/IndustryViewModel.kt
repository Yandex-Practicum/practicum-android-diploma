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
import ru.practicum.android.diploma.presentation.filtration.industry.state.IndustryScreenUiState
import ru.practicum.android.diploma.presentation.filtration.industry.state.IndustryScreenState

class IndustryViewModel(
    private val industryInteractor: IndustryInteractor,
) : ViewModel() {
    private var allIndustries: List<FilterIndustry> = emptyList()

    private val _state = MutableStateFlow(IndustryScreenUiState())
    val state: StateFlow<IndustryScreenUiState> = _state.asStateFlow()

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
            publishState(status = IndustryScreenState.Content(isLoading = true))
            when (val outcome = industryInteractor.getIndustries()) {
                is IndustryResult.Success -> {
                    allIndustries = outcome.industries
                    publishState(status = IndustryScreenState.Content(isLoading = false))
                }
                is IndustryResult.Error,
                is IndustryResult.NoInternet,
                is IndustryResult.ServerError,
                -> publishState(status = IndustryScreenState.Error)
            }
        }
    }

    private fun publishState(
        status: IndustryScreenState = _state.value.status,
        searchQuery: String = _state.value.searchQuery,
        selectedIndustry: FilterIndustry? = _state.value.selectedIndustry,
    ) {
        _state.value = IndustryScreenUiState(
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
