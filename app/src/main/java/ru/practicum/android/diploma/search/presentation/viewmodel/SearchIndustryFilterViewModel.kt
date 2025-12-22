package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.search.domain.model.FilterIndustry

class SearchIndustryFilterViewModel(
//    private val interactor: VacancyInteractor
) : ViewModel() {

    private val _industries = MutableStateFlow<List<FilterIndustry>>(emptyList())
    val industries: StateFlow<List<FilterIndustry>> = _industries

}
