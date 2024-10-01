package ru.practicum.android.diploma.search.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.usecase.VacanciesInteractor

class VacancyListViewModel(
    private val vacancyInteractor: VacanciesInteractor,
    private val application: Application,
) : AndroidViewModel(application) {

    private val vacancies = ArrayList<Vacancy>()

    val adapter = VacancyListAdapter {
        //TODO logic
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val TAG = "VacancyListViewModel"
    }
}
