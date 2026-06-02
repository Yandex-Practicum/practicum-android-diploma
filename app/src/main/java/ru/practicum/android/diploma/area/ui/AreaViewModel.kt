package ru.practicum.android.diploma.area.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.core.domain.models.Area

abstract class AreaViewModel : ViewModel() {
    abstract val state: StateFlow<AreaScreenState>

    abstract fun resetCountry()
    abstract fun resetRegion()
    abstract fun apply()

    abstract fun onBack(country: Area?)
}
