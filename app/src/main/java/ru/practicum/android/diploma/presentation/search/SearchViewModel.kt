package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import ru.practicum.android.diploma.util.Debounce

class SearchViewModel(private val debounce: Debounce, private val searchInteractor: SearchInteractor) : ViewModel() {

    private var searchText: String? = null
    private var searchState = MutableLiveData<SearchState>()
    val trackListLiveData: LiveData<SearchState> = searchState
    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    fun searchDebounce(text: String) {
        if (text.isEmpty() || text == searchText) {
            debounce.cancel()
        } else {
            val debouncedFunction = debounce.debounceFunction<String>(SEARCH_DEBOUNCE_DELAY) { searchText ->
                searchVacancy(searchText)
            }
            debouncedFunction(text)
        }
    }

    fun clearSearchResults() {
        searchState.postValue(SearchState.Default)
    }

    private fun searchVacancy(text: String) {
        searchText = text
        searchState.postValue(SearchState.Loading)
        viewModelScope.launch {
            searchInteractor
                .searchVacancies(text, 1)
                .collect { pair -> processResult(pair.first, pair.second) }
        }
    }

    private fun processResult(vacancies: List<DomainVacancy>?, errorMessage: String?) {
        if (vacancies != null) {
            if (vacancies.isEmpty()) {
                searchState.postValue(SearchState.NoResults)
            } else {
                searchState.postValue(SearchState.Success(vacancies, 1))
            }
        } else if (errorMessage != null) {
            searchState.postValue(SearchState.Error(errorMessage))
        }
    }

}
