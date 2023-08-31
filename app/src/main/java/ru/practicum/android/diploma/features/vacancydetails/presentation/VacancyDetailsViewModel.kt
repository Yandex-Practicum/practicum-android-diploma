package ru.practicum.android.diploma.features.vacancydetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.features.vacancydetails.presentation.models.VacancyDetailsState

class VacancyDetailsViewModel: ViewModel()  {

    private val _state = MutableLiveData<VacancyDetailsState>()
    val state: LiveData<VacancyDetailsState> get() = _state

    fun getVacancyById(id: String?) {}
}