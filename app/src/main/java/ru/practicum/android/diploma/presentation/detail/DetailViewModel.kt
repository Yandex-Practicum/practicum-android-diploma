package ru.practicum.android.diploma.presentation.detail

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.detail.DetailInteractor
import ru.practicum.android.diploma.domain.models.detail.Vacancy

class DetailViewModel(val interactor: DetailInteractor) : ViewModel() {

    fun getVacancy():Vacancy{
        return interactor.getVacancy()
    }
}
