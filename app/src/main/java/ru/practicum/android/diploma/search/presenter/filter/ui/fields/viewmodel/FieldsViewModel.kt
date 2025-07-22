package ru.practicum.android.diploma.search.presenter.filter.ui.fields.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.FiltersInteractor
import ru.practicum.android.diploma.search.domain.model.Industry
import ru.practicum.android.diploma.search.presenter.filter.model.FieldsState

class FieldsViewModel(private val filtersInteractor: FiltersInteractor) : ViewModel() {

    private val _state = MutableStateFlow<FieldsState>(FieldsState.Loading)
    val state: StateFlow<FieldsState> = _state

    private var allIndustries = listOf<Industry>()
    private var selectedIndustry: Industry? = null

    init {
        loadIndustries()
    }

    private fun loadIndustries() {
        viewModelScope.launch {
            val (savedIndustry, savedSalary, savedOnlyWithSalary) = filtersInteractor.getSavedFilters()
            filtersInteractor.getIndustries().collect { pair ->
                val industries = pair.first
                val error = pair.second

                when {
                    error != null -> {
                        _state.value = FieldsState.Error(error)
                    }

                    industries.isNullOrEmpty() -> {
                        _state.value = FieldsState.Empty
                    }

                    else -> {
                        allIndustries = industries
                        selectedIndustry = savedIndustry?.let { saved ->
                            allIndustries.find { it.id == saved.id }
                        }
                        _state.value = FieldsState.Content(allIndustries, selectedIndustry)
                    }
                }
            }
        }
    }

    fun onIndustrySelected(industry: Industry) {
        selectedIndustry = if (industry.id == selectedIndustry?.id) {
            null
        } else {
            industry
        }
        val currentState = _state.value
        if (currentState is FieldsState.Content) {
            _state.value = currentState.copy(selectedIndustry = selectedIndustry)
        }

        viewModelScope.launch {
            filtersInteractor.saveFilters(
                industry = selectedIndustry,
                salary = filtersInteractor.getSavedFilters().second,
                onlyWithSalary = filtersInteractor.getSavedFilters().third
            )
        }
    }

    fun filter(query: String) {
        val filteredList = allIndustries.filter {
            it.name.contains(query, ignoreCase = true)
        }

        if (filteredList.isEmpty()) {
            _state.value = FieldsState.Empty
        } else {
            _state.value = FieldsState.Content(filteredList, selectedIndustry)
        }
    }
}
