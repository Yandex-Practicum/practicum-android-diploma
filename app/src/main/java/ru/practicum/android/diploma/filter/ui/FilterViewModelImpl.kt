package ru.practicum.android.diploma.filter.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor

class FilterViewModelImpl(val interactor: FilterInteractor) : FilterViewModel() {
    private val _state = MutableStateFlow<Filters>(Filters())
    override var state: StateFlow<Filters> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.filters().collect {
                _state.value = it
            }
        }
    }
    override fun onQueryChanged(query: String) {
        _state.value = _state.value.copy(salary = query)
        interactor.changeSalary(query)
    }

    override fun onResetSalary() {
        interactor.changeSalary(null)
    }

    override fun onResetArea() {
        interactor.resetArea()
    }

    override fun onResetIndustry() {
        interactor.resetIndustry()
    }

    override fun onToggleSalary() {
        interactor.toggleSalary()
    }

    override fun apply() {
        interactor.apply()
    }

    override fun cancel() {
        interactor.resetTemp()
    }

    override fun reset() {
        interactor.reset()
    }

}
