package ru.practicum.android.diploma.presentation.search

import ru.practicum.android.diploma.util.Debounce
import androidx.lifecycle.ViewModel

class SearchViewModel(private val debounce: Debounce) : ViewModel() {

    fun searchDebounce(text: String) {
        if (text.isEmpty()) {
            debounce.cancel()
        } else {
            val debouncedFunction = debounce.debounceFunction<String>(2000) { searchText ->
                searchVacancy(searchText)
            }
            debouncedFunction(text)
        }
    }

    fun searchVacancy(text: String) {
        // interactor.searchVacancy()
    }

}
