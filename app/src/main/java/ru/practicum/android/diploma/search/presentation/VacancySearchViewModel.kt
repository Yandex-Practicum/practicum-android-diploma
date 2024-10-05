package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.SearchVacancyInteractor
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.util.SingleEventLiveData
import ru.practicum.android.diploma.util.debounce

class VacancySearchViewModel(
    private val interactor: SearchVacancyInteractor,
) : ViewModel() {

    private var latestSearchText: String? = null

    private val vacancyClickEvent = SingleEventLiveData<String>()
    fun getVacancyClickEvent(): LiveData<String> = vacancyClickEvent

    private val stateLiveData = MutableLiveData<VacancySearchScreenState>()
    fun getStateObserve(): LiveData<VacancySearchScreenState> = stateLiveData
    val query = HashMap<String, String>()

    fun loadData(text: String) {
        if (text.isNotEmpty()) {
            stateLiveData.value = VacancySearchScreenState.Loading

            query["text"] = text
            viewModelScope.launch {
                interactor
                    .getVacancyList(query)
                    .collect { foundVacancies ->
                        processingState(foundVacancies)
                    }
            }
        }
    }

    private val tracksSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            loadData(changedText)
        }

    fun searchDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            tracksSearchDebounce(changedText)
        }
    }

    fun onVacancyClick(vacancySearch: VacancySearch) {
        vacancySearch.toString()
        // TODO("Дописыать реализацию сохранения и передачи id")
    }

    private fun processingState(foundVacancies: List<VacancySearch>?) {
        when {
            foundVacancies == null -> {
                stateLiveData.value = VacancySearchScreenState.ServerError
            }

            foundVacancies.isEmpty() -> {
                stateLiveData.value = VacancySearchScreenState.EmptyScreen
            }

            else -> {
                stateLiveData.value = VacancySearchScreenState.Content(foundVacancies)
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
