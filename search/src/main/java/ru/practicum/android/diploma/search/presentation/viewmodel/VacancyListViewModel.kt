package ru.practicum.android.diploma.search.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.practicum.android.diploma.search.domain.usecase.VacanciesInteractor

class VacancyListViewModel(
    private val vacanciesInteractor: VacanciesInteractor,
    application: Application,
) : AndroidViewModel(application)
