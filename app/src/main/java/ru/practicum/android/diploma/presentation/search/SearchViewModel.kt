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

    private val stateLiveData = MutableLiveData<SearchState>()

    private var _iconStateLiveData = MutableLiveData<IconState>()
    val iconStateLiveData: LiveData<IconState> = _iconStateLiveData

    fun observeState(): LiveData<SearchState> = stateLiveData
    private fun renderState(state: SearchState) {
        stateLiveData.postValue(state)
    }

    fun clearInputEditText() {
        lastSearchText = ""
    }

    fun setOnFocus(editText: String?, hasFocus: Boolean) {
        if (hasFocus && editText.isNullOrEmpty()) _iconStateLiveData.postValue(IconState.SearchIcon)
        if (hasFocus && editText!!.isNotEmpty()) _iconStateLiveData.postValue(IconState.CloseIcon)
        if (!hasFocus && editText!!.isNotEmpty()) _iconStateLiveData.postValue(IconState.SearchIcon)
        if (!hasFocus && editText.isNullOrEmpty()) _iconStateLiveData.postValue(IconState.SearchIcon)
    }

    fun searchDebounce(changedText: String) {
        if (lastSearchText == changedText) {
            return
        }
        this.lastSearchText = changedText


        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY_MILLIS)
            searchVacancy(changedText)
        }
    }

    private fun searchVacancy(searchText: String) {
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
                renderState(
                    SearchState.Content(
                        vacancies = vacancies,
                        foundValue = vacancies[0].found
                    )
                )
            }
        }
    }

    companion object {
        const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
    }
}
