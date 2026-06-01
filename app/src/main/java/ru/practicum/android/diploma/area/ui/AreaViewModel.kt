package ru.practicum.android.diploma.area.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class AreaViewModel : ViewModel() {
    abstract val state: StateFlow<AreaScreenState>

    abstract fun resetCountry()
    abstract fun resetRegion()
}
