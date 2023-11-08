package ru.practicum.android.diploma.presentation.similar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.ResourceProvider
import ru.practicum.android.diploma.domain.SearchState
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.similar.SimilarInteractor


class SimilarViewModel(
    private val interactor: SimilarInteractor,
    private val resourceProvider: ResourceProvider,
) : ViewModel() {
    private val stateLiveData = MutableLiveData<SearchState>()
    fun observeState(): LiveData<SearchState> = stateLiveData
    private fun renderState(state: SearchState) {
        stateLiveData.postValue(state)
    }

    fun searchVacancy(searchText: String) {
        if (searchText.isNotEmpty()) {
            renderState(SearchState.Loading)

            viewModelScope.launch {
                interactor.loadVacancies(searchText)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }


    private fun processResult(foundVacancies: List<Vacancy>?, errorMessage: String?) {
        val vacancies = mutableListOf<Vacancy>()
        if (foundVacancies != null) {
            vacancies.addAll(foundVacancies)
        }
        when {
            errorMessage != null -> {
                renderState(
                    SearchState.Error(
                        errorMessage = resourceProvider.getString(R.string.no_connection)
                    )
                )
            }

            vacancies.isEmpty() -> {
                renderState(
                    SearchState.Empty(
                        message = resourceProvider.getString(R.string.no_vacancies)
                    )
                )
            }

            else -> {
                renderState(
                    SearchState.Content(
                        vacancies = vacancies,
                        foundValue = vacancies[0].found
                    )
                )
            }
        }
    }
}