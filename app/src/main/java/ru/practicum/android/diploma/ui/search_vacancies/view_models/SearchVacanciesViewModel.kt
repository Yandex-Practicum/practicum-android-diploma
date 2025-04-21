package ru.practicum.android.diploma.ui.search_vacancies.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.IVacancyInteractor
import ru.practicum.android.diploma.domain.models.SearchVacanciesState
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.debounce

class SearchVacanciesViewModel(private val vacancyInteractor: IVacancyInteractor) : ViewModel() {
    private val stateLiveData = MutableLiveData<SearchVacanciesState>()
    private val vacancies = ArrayList<Vacancy>()

    fun observeState(): LiveData<SearchVacanciesState> = stateLiveData

    fun searchDebounce(changedText: String) {
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) {
            searchVacancies(
                changedText
            )
        }
    }

    private fun renderState(state: SearchVacanciesState) {
        stateLiveData.postValue(state)
    }

    private fun searchVacancies(expression: String) {
        if (expression.isNotEmpty()) {
            renderState(SearchVacanciesState.Loading)
            viewModelScope.launch {
                vacancyInteractor.searchVacancies(expression).collect { (foundedVacancies, errorMessage) ->
                    vacancies.clear()
                    if (!foundedVacancies.isNullOrEmpty()) {
                        vacancies.addAll(foundedVacancies)
                        renderState(SearchVacanciesState.VacanciesList(vacancies))
                    } else if (errorMessage.isNullOrEmpty()) {
                        renderState(SearchVacanciesState.NetworkError)
                    } else {
                        renderState(SearchVacanciesState.NothingFound)
                    }
                }
            }
        } else {
            renderState(SearchVacanciesState.Empty)
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
