package ru.practicum.android.diploma.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.search.VacanciesSearchRepository
import ru.practicum.android.diploma.domain.models.vacacy.VacancyResponse

class SearchViewModel(
    private val vacancySearchRepository: VacanciesSearchRepository
) : ViewModel() {

    private val stateLiveData = MutableLiveData<SearchViewState>()

    fun observeState(): LiveData<SearchViewState> = stateLiveData

    fun search(text: String) {
        viewModelScope.launch {
            stateLiveData.postValue(SearchViewState.Loading)
            vacancySearchRepository.getVacancies(mapOf("text" to text)).collect {
                if (it.first != null) {
                    val vacancies = (it.first as VacancyResponse).items
                    if (vacancies.isEmpty()) {
                        stateLiveData.postValue(SearchViewState.EmptyVacancies)
                    } else {
                        stateLiveData.postValue(SearchViewState.Content(vacancies))
                    }

                } else if (it.second != null) {
                    stateLiveData.postValue(SearchViewState.NoInternet)
                }
            }
        }
    }
}
