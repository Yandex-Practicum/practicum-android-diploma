package ru.practicum.android.diploma.search.presenter.search

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
import ru.practicum.android.diploma.search.presenter.model.VacancyPreviewUi
import ru.practicum.android.diploma.util.VacancyFormatter

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
                val data = pair.first
                val message = pair.second
                when {
                    !data.isNullOrEmpty() -> {
                        val uiData = data.map { it.toUiModel() }
                        _state.value = SearchState.Content(uiData)
                    }

                    message == FailureType.NotFound -> _state.value = SearchState.NotFound
                    message == FailureType.ApiError || message == FailureType.NoInternet -> {
                        _state.value = SearchState.Error
                    }
                }
            }
        }
    }

    private fun VacancyPreview.toUiModel(): VacancyPreviewUi {
        return VacancyPreviewUi(
            id = id,
            found = found,
            name = name,
            employerName = employerName,
            salary = VacancyFormatter.formatSalary(from, to, currency),
            logoUrl = url
        )
    }
}
