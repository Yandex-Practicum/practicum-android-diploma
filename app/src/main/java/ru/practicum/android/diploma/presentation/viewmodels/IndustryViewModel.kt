package ru.practicum.android.diploma.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import ru.practicum.android.diploma.domain.api.FilterIndustriesInteractor
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.debounce

class IndustryViewModel(val interactor: FilterIndustriesInteractor) : ViewModel() {
    private val liveData = MutableLiveData<IndustryState>(IndustryState.Empty(""))
    fun observeLiveData(): LiveData<IndustryState> = liveData

    private var lastSearchRequest: String = ""

    private val searchDebounceFunction = debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { query ->
        performSearch(query)
    }

    fun searchDebounce(searchQuery: String) {
        if (searchQuery == lastSearchRequest) {
            return
        }

        lastSearchRequest = searchQuery

        // Если запрос пустой, показываем пустое состояние
        if (searchQuery.isEmpty()) {
            renderState(IndustryState.Empty(""))
            return
        }

        // Запускаем debounce поиск
        searchDebounceFunction(searchQuery)
    }

    private fun performSearch(searchQuery: String) {
        viewModelScope.launch {
            try {
                renderState(IndustryState.IsLoading)
                interactor.getIndustries(searchQuery)
                    .collect { result ->
                        processResult(result)
                    }
            } catch (e: Exception) {
                renderState(IndustryState.Error(""))
            }
        }
    }

    private fun processResult(industries: List<Industry>) {
        if (industries.isEmpty()) {
            renderState(IndustryState.Empty(""))
        } else {
            renderState(IndustryState.Content(industries))
        }
    }

    private fun renderState(state: IndustryState) {
        liveData.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
