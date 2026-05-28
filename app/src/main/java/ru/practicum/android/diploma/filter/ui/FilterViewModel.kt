package ru.practicum.android.diploma.filter.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.core.domain.models.Filters

abstract class FilterViewModel : ViewModel() {
    abstract var state: StateFlow<Filters>
    abstract fun onQueryChanged(query: String): Unit
    abstract fun onFocusChanged(focus: Boolean): Unit
    abstract fun onResetArea(): Unit
    abstract fun onResetIndustry(): Unit
    abstract fun onToggleSalary(): Unit

}
