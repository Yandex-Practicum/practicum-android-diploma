package ru.practicum.android.diploma.search.presenter.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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

    private var searchJob: Job? = null

    fun searchVacancies(text: String, area: String? = null) {
        if (text.isBlank()) {
            searchJob?.cancel()
            _state.value = SearchState.Empty
            return
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)

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
                    message == FailureType.NotFound || data.isNullOrEmpty() -> {
                        _state.value = SearchState.NotFound
                    }
                    else -> {
                        _state.value = SearchState.Error
                    }
                }
            }
        }
    }

    private fun VacancyPreview.toUiModel(): VacancyPreviewUi {
        return VacancyPreviewUi(
            id = id,
            name = name,
            employerName = employerName,
            salary = VacancyFormatter.formatSalary(from, to, currency),
            logoUrl = url
        )
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
// package ru.practicum.android.diploma.search.presenter.search
//
// import androidx.lifecycle.ViewModel
// import androidx.lifecycle.viewModelScope
// import kotlinx.coroutines.flow.MutableStateFlow
// import kotlinx.coroutines.flow.StateFlow
// import kotlinx.coroutines.flow.onStart
// import kotlinx.coroutines.launch
// import ru.practicum.android.diploma.search.domain.api.SearchInteractor
// import ru.practicum.android.diploma.search.domain.model.FailureType
// import ru.practicum.android.diploma.search.domain.model.VacancyPreview
// import ru.practicum.android.diploma.search.presenter.model.SearchState
// import ru.practicum.android.diploma.search.presenter.model.VacancyPreviewUi
// import ru.practicum.android.diploma.util.VacancyFormatter
//
// class SearchViewModel(
//     private val searchInteractor: SearchInteractor
// ) : ViewModel() {
//
//     private val _state = MutableStateFlow<SearchState>(SearchState.Empty)
//     val state: StateFlow<SearchState> = _state
//
//     fun searchVacancies(text: String, area: String? = null) {
//         viewModelScope.launch {
//             searchInteractor.getVacancies(text, area).onStart {
//                 _state.value = SearchState.Loading
//             }.collect { pair ->
//                 // val data = pair.first
//                 val data = getMockVacancies()
//                 val message = pair.second
//                 when {
//                     !data.isNullOrEmpty() -> {
//                         val uiData = data.map { it.toUiModel() }
//                         _state.value = SearchState.Content(uiData)
//                     }
//
//                     message == FailureType.NotFound -> _state.value = SearchState.NotFound
//                     message == FailureType.ApiError || message == FailureType.NoInternet -> {
//                         _state.value = SearchState.Error
//                     }
//                 }
//             }
//         }
//     }
//
//     private fun getMockVacancies(): List<VacancyPreview> {
//         return listOf(
//             VacancyPreview(
//                 id = 2,
//                 name = "Backend Developer",
//                 employerName = "Yandex",
//                 from = 120_000,
//                 to = null,
//                 currency = "RUB",
//                 url = "https://example.com/logo2.png"
//             ),
//             VacancyPreview(
//                 id = 3,
//                 name = "Frontend Developer",
//                 employerName = "Mail.ru",
//                 from = null,
//                 to = 200_000,
//                 currency = "RUB",
//                 url = null
//             ),
//             VacancyPreview(
//                 id = 4,
//                 name = "Data Scientist",
//                 employerName = "Sber",
//                 from = null,
//                 to = null,
//                 currency = null,
//                 url = "https://example.com/logo4.png"
//             )
//         )
//     }
//
//     private fun VacancyPreview.toUiModel(): VacancyPreviewUi {
//         return VacancyPreviewUi(
//             id = id,
//             name = name,
//             employerName = employerName,
//             salary = VacancyFormatter.formatSalary(from, to, currency),
//             logoUrl = url
//         )
//     }
// }
