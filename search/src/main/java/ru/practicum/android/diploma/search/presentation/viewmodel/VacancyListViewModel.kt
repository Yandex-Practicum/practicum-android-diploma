package ru.practicum.android.diploma.search.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.usecase.VacanciesInteractor

class VacancyListViewModel(
    private val vacancyInteractor: VacanciesInteractor,
    private val application: Application,
) : AndroidViewModel(application) {

    private val vacancies = ArrayList<Vacancy>()

    val adapter = VacancyListAdapter {
        TODO("not implemented4")
    }

    private val _vacanciesStateLiveData = MutableLiveData<VacancyListState>()
    fun observeVacanciesState(): LiveData<VacancyListState> = _vacanciesStateLiveData

    fun searchVacancies(vacancyKeywords: String?) {
        if (vacancyKeywords.isNullOrEmpty()) return
        renderState(VacancyListState.Loading)
        viewModelScope.launch {
            vacancyInteractor.searchVacancies(mapOf("text" to vacancyKeywords)).collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    fun processResult(foundVacancies: List<Vacancy>?, errorMessage: String?) {
        if (foundVacancies != null) {
            vacancies.clear()
            vacancies.addAll(foundVacancies)
        }
        when {
            errorMessage != null -> renderState(VacancyListState.Error(errorMessage))
            vacancies.isEmpty() -> renderState(VacancyListState.Empty)
            else -> renderState(VacancyListState.Content(vacancies))
        }
    }

    private fun renderState(state: VacancyListState) {
        _vacanciesStateLiveData.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val TAG = "VacancyListViewModel"
    }
}
