package ru.practicum.android.diploma.vacancy.details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.search.domain.model.VacancyDetail

class VacancyDetailsViewModel(
//    private val interactor: VacancyInteractor
) : ViewModel() {

    private val _vacancy = MutableStateFlow<VacancyDetail?>(null)
    val vacancy: StateFlow<VacancyDetail?> = _vacancy

}
