package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.ResourceProvider
import ru.practicum.android.diploma.domain.SearchState
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchViewModel(
    private val interactor: SearchInteractor,
    private val resourceProvider: ResourceProvider,
) : ViewModel() {

    private var lastSearchText: String? = null
    private var searchJob: Job? = null
    private val pageStart: Int = 0
    private var totalPages: Int = 1
    private var currentPage: Int = pageStart

    private val stateLiveData = MutableLiveData<SearchState>()
    fun observeState(): LiveData<SearchState> = stateLiveData

    private val stateNextLiveData = MutableLiveData<SearchState>()
    fun observeNextState(): LiveData<SearchState> = stateNextLiveData
    private fun renderState(state: SearchState) {
        stateLiveData.postValue(state)
    }

    private fun renderNextState(state: SearchState) {
        stateNextLiveData.postValue(state)
    }

    fun clearInputEditText() {
        lastSearchText = ""
    }

    fun searchDebounce(changedText: String) {
        if (lastSearchText == changedText) {
            return
        }
        this.lastSearchText = changedText
        currentPage = 0
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY_MILLIS)
            searchVacancy(changedText, 0)
        }
    }

    private fun searchVacancy(searchText: String, pageCount: Int) {
        if (searchText.isNotEmpty()) {
            if (pageCount == 0) renderState(SearchState.Loading) else renderNextState(SearchState.Loading)
            viewModelScope.launch {
                interactor.loadVacancies(searchText, pageCount)
                    .collect { pair ->
                        if (pageCount == 0) processResult(pair.first, pair.second)
                        else processNextResult(pair.first, pair.second)
                    }
            }
        }
    }

    private fun processNextResult(foundVacancies: List<Vacancy>?, errorMessage: String?) {
        val vacancies = mutableListOf<Vacancy>()
        if (foundVacancies != null) {
            vacancies.addAll(foundVacancies)
        }
        when {
            errorMessage != null -> {
                renderNextState(
                    SearchState.Error(
                        errorMessage = resourceProvider.getString(R.string.no_internet)
                    )
                )
            }

            vacancies.isEmpty() -> {
                renderNextState(
                    SearchState.Empty(
                        message = resourceProvider.getString(R.string.no_vacancies)
                    )
                )
            }

            else -> {
                renderNextState(
                    SearchState.Content(
                        vacancies = vacancies,
                        foundValue = vacancies[0].found
                    )
                )
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
                        errorMessage = resourceProvider.getString(R.string.no_internet)
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
                totalPages = vacancies[0].found.div(PER_PAGE)
                renderState(
                    SearchState.Content(
                        vacancies = vacancies,
                        foundValue = vacancies[0].found
                    )
                )
            }
        }
    }

    fun addSearch(inputText: String) {
        currentPage += 1
        if (currentPage <= totalPages) searchVacancy(inputText, currentPage)
        else renderNextState(
            SearchState.Empty(
                message = resourceProvider.getString(R.string.no_vacancies)
            ))
    }

    companion object {
        const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
        const val PER_PAGE = 20
    }
}
