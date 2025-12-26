package ru.practicum.android.diploma.vacancy.details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.vacancy.details.domain.api.VacancyDetailsInteractor

class VacancyDetailsViewModel(
    private val interactor: VacancyDetailsInteractor
) : ViewModel() {

    private val _vacancy = MutableStateFlow<VacancyDetail?>(null)
    val vacancy: StateFlow<VacancyDetail?> = _vacancy

    fun getShareUrl(): String? {
        return _vacancy.value?.url
    }

}
