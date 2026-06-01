package ru.practicum.android.diploma.vacancy.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class VacancyViewModel : ViewModel() {
    abstract val state: StateFlow<VacancyState>
    abstract fun onFavoriteClicked()
}
