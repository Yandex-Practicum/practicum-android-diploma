package ru.practicum.android.diploma.country.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.core.domain.models.Area

abstract class CountryViewModel : ViewModel() {
    abstract var state: StateFlow<CountryScreenState>
    abstract fun selectCountry(country: Area)
}
