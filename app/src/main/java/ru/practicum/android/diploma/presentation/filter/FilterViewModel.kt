package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.practicum.android.diploma.domain.interactors.FilterInteractor
import ru.practicum.android.diploma.domain.models.FilterSettings

class FilterViewModel(
    private val filterInteractor: FilterInteractor,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        FilterUiState(settings = filterInteractor.getFilterSettings())
    )
    val uiState: StateFlow<FilterUiState> = _uiState.asStateFlow()

    fun onSalaryChanged(salary: String) {
        updateSettings { settings ->
            settings.copy(salary = salary.filter(Char::isDigit))
        }
    }

    fun onSalaryClearClicked() {
        updateSettings { settings ->
            settings.copy(salary = "")
        }
    }

    fun onOnlyWithSalaryChanged(checked: Boolean) {
        updateSettings { settings ->
            settings.copy(onlyWithSalary = checked)
        }
    }

    fun onResetClicked() {
        filterInteractor.clearFilterSettings()
        _uiState.update { FilterUiState(settings = FilterSettings()) }
    }

    private fun updateSettings(transform: (FilterSettings) -> FilterSettings) {
        _uiState.update { state ->
            val newSettings = transform(state.settings)
            filterInteractor.saveFilterSettings(newSettings)
            state.copy(settings = newSettings)
        }
    }
}
