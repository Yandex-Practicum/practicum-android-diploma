package ru.practicum.android.diploma.vacancy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.vacancy.domain.interactor.VacancyInteractor

class VacancyScreenViewModel(
    private val vacancyInteractor: VacancyInteractor
) : ViewModel()
