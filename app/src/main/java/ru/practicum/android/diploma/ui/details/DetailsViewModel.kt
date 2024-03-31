package ru.practicum.android.diploma.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor

class DetailsViewModel(
    private val interactor: VacancyDetailsInteractor
) : ViewModel() {

    fun onViewCreated() {
        viewModelScope.launch {
            interactor.getVacancyDetails("93176064").collect() { }
        }
    }
}
