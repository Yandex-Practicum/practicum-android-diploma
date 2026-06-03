package ru.practicum.android.diploma.region.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class RegionViewModel : ViewModel() {
    abstract val query: StateFlow<String>
    abstract val state: StateFlow<RegionScreenState>

    abstract fun onQueryChanged(query: String)
    abstract fun onSearchIconClicked()
}
