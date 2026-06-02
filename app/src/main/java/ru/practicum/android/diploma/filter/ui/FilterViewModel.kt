package ru.practicum.android.diploma.filter.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.core.domain.models.Filters

abstract class FilterViewModel : ViewModel() {
    abstract var state: StateFlow<Filters>
    abstract val isModified: StateFlow<Boolean>
    abstract fun onQueryChanged(query: String): Unit
    abstract fun onResetSalary(): Unit
    abstract fun onResetArea(): Unit
    abstract fun onResetIndustry(): Unit
    abstract fun onToggleSalary(): Unit
    abstract fun apply(): Unit
    abstract fun cancel(): Unit
    abstract fun reset()
}
