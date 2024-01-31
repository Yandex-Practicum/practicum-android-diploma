package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.models.ErrorNetwork
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchViewModel(
    private var searchInteractor: SearchInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<SearchState>()

    private fun renderState(state: SearchState) {
        stateLiveData.postValue(state)
    }

    fun getStateLiveData(): LiveData<SearchState> {
        return stateLiveData
    }

    fun searchRequest(searchText: String) {
        if (searchText.isNotEmpty()) {
            renderState(SearchState.Loading)
            viewModelScope.launch {
                searchInteractor
                    .search(searchText)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }

    val vacancys = mutableListOf<Vacancy>()
    private fun processResult(searchVacancys: List<Vacancy>?, errorMessage: ErrorNetwork?) {
        if (searchVacancys != null) {
            vacancys.addAll(searchVacancys)
        }

        when {
            errorMessage != null -> {
                renderState(SearchState.Error)
            }

            vacancys.isEmpty() -> {
                renderState(SearchState.EmptySearch)
            }

            else -> {
                renderState(SearchState.SearchContent(vacancys = vacancys))
            }
        }
    }
}
