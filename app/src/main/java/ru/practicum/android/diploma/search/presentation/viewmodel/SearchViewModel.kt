package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.search.domain.model.VacancyDetail

class SearchViewModel(
//    private val interactor: VacancyInteractor
) : ViewModel() {

    private val _vacancies = MutableStateFlow<List<VacancyDetail>>(emptyList())
    val vacancies: StateFlow<List<VacancyDetail>> = _vacancies

}
