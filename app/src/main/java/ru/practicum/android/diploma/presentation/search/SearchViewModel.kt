package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.util.Debounce

class SearchViewModel(private val debounce: Debounce) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    fun searchDebounce(text: String) {
        if (text.isEmpty()) {
            debounce.cancel()
        } else {
            val debouncedFunction = debounce.debounceFunction<String>(SEARCH_DEBOUNCE_DELAY) { searchText ->
                searchVacancy(searchText)
            }
            debouncedFunction(text)
        }
    }

    fun clearSearchResults() {
        // searchState.postValue(SearchState.Default())
    }

    fun searchVacancy(text: String) {
        // interactor.searchVacancy()
    }

}
