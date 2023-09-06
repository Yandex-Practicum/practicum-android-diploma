package ru.practicum.android.diploma.similars

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.details.domain.DetailsInteractor
import ru.practicum.android.diploma.details.ui.DetailsScreenState
import ru.practicum.android.diploma.filter.data.model.NetworkResponse
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.models.SearchScreenState
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class SimilarVacanciesViewModel @Inject constructor(
    logger: Logger,
    private val detailsInteractor: DetailsInteractor,
) : BaseViewModel(logger) {

    private val _uiState = MutableStateFlow<SimilarVacanciesState>(SimilarVacanciesState.Empty)
    val uiState: StateFlow<SimilarVacanciesState> = _uiState

    fun getSimilarVacancies(vacancyId: String) {
        log(thisName, "getSimilarVacancies(vacancyId: $vacancyId)")
        _uiState.value = SimilarVacanciesState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            detailsInteractor.getSimilarVacancies(vacancyId).collect { result ->
                when (result) {
                    is NetworkResponse.Success -> {
                        log(thisName, "NetworkResponse.Success -> ${result.thisName}")
                        _uiState.value = SimilarVacanciesState.Content(result.data)
                    }
                    is NetworkResponse.Error -> {
                        log(thisName, "NetworkResponse.Error -> ${result.message}")
                        _uiState.value = SimilarVacanciesState.Empty
                    }
                    is NetworkResponse.Offline -> {
                        log(thisName, "NetworkResponse.Offline-> ${result.message}")
                        _uiState.value = SimilarVacanciesState.Offline(result.message)
                    }
                    is NetworkResponse.NoData -> {
                        log(thisName, "NetworkResponse.NoData -> ${result.message}")
                        _uiState.value = SimilarVacanciesState.Empty
                    }
                }
            }
        }
    }
}