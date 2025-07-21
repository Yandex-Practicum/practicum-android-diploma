package ru.practicum.android.diploma.search.presenter.filter.ui.fields.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.FiltersInteractor
import ru.practicum.android.diploma.search.domain.model.FailureType
import ru.practicum.android.diploma.search.domain.model.Industry
import ru.practicum.android.diploma.search.presenter.filter.model.FieldsState

class FieldsViewModel(private val filtersInteractor: FiltersInteractor) : ViewModel() {

    private val _state = MutableStateFlow<FieldsState>(FieldsState.Loading)
    val state: StateFlow<FieldsState> = _state

    private var allIndustries = listOf<Industry>()

    init {
        loadIndustries()
    }

    private fun loadIndustries() {
        viewModelScope.launch {
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
                        _state.value = FieldsState.Content(allIndustries)
                    }
                }
            }
        }
    }

    fun filter(query: String) {
        if (query.isEmpty()) {
            _state.value = FieldsState.Content(allIndustries)
            return
        }

        val filteredList = allIndustries.filter {
            it.name.contains(query, ignoreCase = true)
        }

        if (filteredList.isEmpty()) {
            _state.value = FieldsState.Empty
        } else {
            _state.value = FieldsState.Content(filteredList)
        }
    }

    fun onIndustrySelected(industry: Industry) {
        // TODO: Здесь будет логика сохранения выбранной отрасли.
    }
}
