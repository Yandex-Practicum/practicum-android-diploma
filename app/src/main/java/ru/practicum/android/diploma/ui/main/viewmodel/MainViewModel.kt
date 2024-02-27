package ru.practicum.android.diploma.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.main.SearchInteractor
import ru.practicum.android.diploma.domain.models.main.Vacancy
import ru.practicum.android.diploma.ui.main.MainViewState
import ru.practicum.android.diploma.ui.main.SearchState

class MainViewModel(
    val interactor: SearchInteractor
) : ViewModel() {
    private val state = MutableStateFlow(MainViewState())
    fun observeState() = state.asStateFlow()

    fun onSearch(text: String) {
        if (text.isEmpty()) {
            state.update { it.copy(state = null) }
            return
        }

        state.update { it.copy(state = SearchState.Loading) }
        viewModelScope.launch {
            searchRequest(text)
        }
    }

    private fun searchRequest(text: String) {
        if (text.isNotEmpty()) {
            state.update { it.copy(state = SearchState.Loading) }

            viewModelScope.launch {
                interactor.searchTrack(mapOf("text" to text))
                    .collect { vacancies ->
                        processResult(vacancies.first, vacancies.second)
                    }
            }
        }
    }

    private fun processResult(foundVacancy: List<Vacancy>?, errorMessage: Int?) {
        val vacancy = mutableListOf<Vacancy>()

        if (foundVacancy != null) {
            vacancy.addAll(foundVacancy)
        }

        when {
            vacancy.isEmpty() -> {
                state.update { it.copy(state = SearchState.Empty) }
                Log.d("StateSearch", "Список пуст")
            }

            errorMessage != null -> {
                state.update { it.copy(state = SearchState.Error) }
                Log.d("StateSearch", "Список ошибка")
            }

            else -> {
                state.update { it.copy(state = SearchState.Content(vacancy)) }
                Log.d("StateSearch", "Есть что-то = $vacancy")
            }
        }
    }
}
