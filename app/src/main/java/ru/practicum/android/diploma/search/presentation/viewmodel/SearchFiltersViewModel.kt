package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.search.domain.model.VacancyFilter

class SearchFiltersViewModel : ViewModel() {

    private val _filters = MutableStateFlow(VacancyFilter())
    val filters: StateFlow<VacancyFilter> = _filters

}
