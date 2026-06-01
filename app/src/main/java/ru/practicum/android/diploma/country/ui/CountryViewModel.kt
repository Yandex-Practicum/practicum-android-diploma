package ru.practicum.android.diploma.country.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class CountryViewModel : ViewModel() {
    abstract var state: StateFlow<CountryScreenState>
}
