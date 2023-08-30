package ru.practicum.android.diploma.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.data.ResourceProvider
import ru.practicum.android.diploma.search.domain.SearchInteractor
import ru.practicum.android.diploma.search.domain.SearchState
import ru.practicum.android.diploma.search.domain.models.Vacancy

class SearchViewModel(
    private val interactor: SearchInteractor,
    private val resourceProvider: ResourceProvider,
) : ViewModel() {

    private var lastSearchText: String? = null
    /*  private var debounceJob: Job? = null
      private var searchJob: Job? = null
      private var getHistoryTracksJob: Job? = null*/

    private val stateLiveData = MutableLiveData<SearchState>()

    fun observeState(): LiveData<SearchState> = stateLiveData
    private fun renderState(state: SearchState) {
        stateLiveData.postValue(state)
    }

    fun onSearchTextChanged(changedText: String) {

        if (changedText.isNullOrEmpty()) {
            /*       debounceJob?.cancel()
                   searchJob?.cancel()*/
            lastSearchText = null
            //  renderState(SearchState.HistroryContent(historyVacancies = trackHistory))
        } else {
            searchDebounce(changedText)
        }
    }

    fun searchDebounce(changedText: String) {

        if (lastSearchText == changedText) {
            return
        } else {
            lastSearchText = changedText
            //     debounceJob?.cancel()
            //      debounceJob =
            viewModelScope.launch {
                //          delay(SEARCH_DEBOUNCE_DELAY)
                searchVacancy(changedText)
            }

        }
    }

    fun refreshSearchTrack(newSearchText: String) {
        searchVacancy(newSearchText)
    }

    fun loadTrackList(editText: String?) {
        if (editText.isNullOrEmpty()) {
            // renderState(SearchState.HistroryContent(historyVacancies = trackHistory))
        }
    }

    fun clearInputEditText() {
        /*    debounceJob?.cancel()
            searchJob?.cancel()*/
        lastSearchText = null
        //    renderState(SearchState.HistroryContent(historyVacancies = trackHistory))
    }

    fun setOnFocus(editText: String?, hasFocus: Boolean) {
        if (hasFocus && editText.isNullOrEmpty()
        //  && trackHistory.isNotEmpty()
        ) {
            //   renderState(SearchState.HistroryContent(historyVacancies = trackHistory))
        }
    }

    fun searchVacancy(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(SearchState.Loading)

            //   searchJob =
            viewModelScope.launch {
                interactor.loadVacancies(newSearchText)
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
                        message = resourceProvider.getString(R.string.nothing_found)
                    )
                )
            }

            else -> {
                renderState(SearchState.VacancyContent(vacancies = vacancies))
            }
        }
    }

    companion object {
        //     const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}