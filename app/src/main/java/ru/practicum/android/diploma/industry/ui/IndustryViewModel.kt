package ru.practicum.android.diploma.industry.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.core.domain.models.Industry

abstract class IndustryViewModel : ViewModel() {
    abstract val query: StateFlow<String>
    abstract var state: StateFlow<IndustryScreenState>

    abstract fun onSearchIconClicked()

    abstract fun onQueryChanged(query: String)

    abstract fun applyFilter(industry: Industry)
}
