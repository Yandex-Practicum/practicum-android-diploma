package ru.practicum.android.diploma.vacancy.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.vacancy.domain.api.VacancyInteractor

class VacancyViewModelImpl(val id: String, val interactor: VacancyInteractor) : VacancyViewModel() {
    init {
        // TODO удалить после реализации VacancyViewModelImpl
        // пример обращения к interactor
        viewModelScope.launch {
            print(interactor.getById("0001db2d-1366-379d-98ce-6f965bbc7816"))
        }
    }
}
