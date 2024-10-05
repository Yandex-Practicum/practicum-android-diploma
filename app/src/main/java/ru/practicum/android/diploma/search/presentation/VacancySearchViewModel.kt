package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.SearchVacancyInteractor
import ru.practicum.android.diploma.search.domain.models.VacancySearch

class VacancySearchViewModel(
    private val interactor: SearchVacancyInteractor,
) : ViewModel() {

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
}
