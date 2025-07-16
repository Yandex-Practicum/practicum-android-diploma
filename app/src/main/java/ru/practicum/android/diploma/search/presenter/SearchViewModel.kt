package ru.practicum.android.diploma.search.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.FailureType
import ru.practicum.android.diploma.search.domain.model.VacancyPreview
import ru.practicum.android.diploma.search.presenter.model.SearchState

class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<SearchState>(SearchState.Empty)
    val state: StateFlow<SearchState> = _state

    fun searchVacancies(text: String, area: String? = null) {
        viewModelScope.launch {
            searchInteractor.getVacancies(text, area).onStart {
                _state.value = SearchState.Loading
            }.collect { pair ->
                // val data = pair.first
                val data = listOf(
                    VacancyPreview(
                        id = 1,
                        name = "Android Developer",
                        employerName = "Google",
                        from = 150_000,
                        to = 250_000,
                        currency = "RUB",
                        url = null
                    ),
                    VacancyPreview(
                        id = 2,
                        name = "Backend Developer",
                        employerName = "Yandex",
                        from = 120_000,
                        to = null,
                        currency = "RUB",
                        url = null
                    ),
                )
                val message = pair.second
                when {
                    !data.isNullOrEmpty() -> {
                        _state.value = SearchState.Content(data)
                    }

                    message == FailureType.NotFound -> _state.value = SearchState.NotFound
                    message == FailureType.ApiError || message == FailureType.NoInternet -> {
                        _state.value = SearchState.Error
                    }
                }
            }
        }
    }
}
