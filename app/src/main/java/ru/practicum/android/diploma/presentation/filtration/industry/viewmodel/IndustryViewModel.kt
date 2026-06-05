package ru.practicum.android.diploma.presentation.filtration.industry.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.FiltrationInteractor
import ru.practicum.android.diploma.domain.api.IndustryInteractor
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.domain.models.IndustryResult
import ru.practicum.android.diploma.presentation.filtration.industry.state.IndustryScreenUiState
import ru.practicum.android.diploma.presentation.filtration.industry.state.IndustryScreenState
import ru.practicum.android.diploma.presentation.filtration.state.FiltrationUIState

class IndustryViewModel(
    private val industryInteractor: IndustryInteractor,
    private val filtrationInteractor: FiltrationInteractor,
) : ViewModel() {
    private var allIndustries: List<FilterIndustry> = emptyList()

    private val _state = MutableStateFlow(IndustryScreenUiState())
    val state: StateFlow<IndustryScreenUiState> = _state.asStateFlow()

    init {
        loadIndustries()
    }

    fun loadIndustry() {
        viewModelScope.launch(Dispatchers.IO) {
            val filters = filtrationInteractor.getFilter()
            if (filters.industryId !=null && filters.industryName != null) {
                publishState(selectedIndustry = FilterIndustry(filters.industryId, filters.industryName))
            }
        }
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

    suspend fun saveSelectedIndustry() {
        val selectedIndustry = _state.value.selectedIndustry ?: return
        withContext(Dispatchers.IO) {
            val currentFilters = filtrationInteractor.getFilter()
            filtrationInteractor.saveFilter(
                currentFilters.copy(
                    industryId = selectedIndustry.id,
                    industryName = selectedIndustry.name,
                ),
            )
        }
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
