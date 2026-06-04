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
            settings.copy(salary = salary.filter(Char::isDigit).take(MAX_SALARY_LENGTH))
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
        _uiState.update { state ->
            state.copy(settings = FilterSettings())
        }
    }

    fun loadFilterSettings() {
        val currentSettings = filterInteractor.getFilterSettings()
        _uiState.update { state ->
            state.copy(
                settings = currentSettings,
                initialSettings = currentSettings
            )
        }
    }

    private fun updateSettings(transform: (FilterSettings) -> FilterSettings) {
        val currentSettings = _uiState.value.settings
        val newSettings = transform(currentSettings)
        if (newSettings != currentSettings) {
            filterInteractor.saveFilterSettings(newSettings)
            _uiState.update { state ->
                state.copy(settings = newSettings)
            }
        }
    }

    companion object {
        private const val MAX_SALARY_LENGTH = 9
    }
}
