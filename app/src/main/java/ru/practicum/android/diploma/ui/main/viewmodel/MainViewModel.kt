package ru.practicum.android.diploma.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.ui.main.MainViewState
import ru.practicum.android.diploma.ui.main.SearchState

class MainViewModel : ViewModel() {
    private val state = MutableStateFlow(MainViewState())
    fun observeState() = state.asStateFlow()

    fun onSearch(text: String) {
        if (text.isEmpty()) {
            state.update { it.copy(state = null) }
            return
        }
        state.update { it.copy(state = SearchState.Loading) }

        viewModelScope.launch {
            delay(2000) //TODO SearchVacancyInteractor(text)
            state.update { it.copy(state = SearchState.Error) }
        }
    }
}
