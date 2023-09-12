package ru.practicum.android.diploma.similars

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.details.domain.DetailsInteractor
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.functional.Failure
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
            detailsInteractor.getSimilarVacancies(vacancyId).fold(
                ::handleFailure,
                ::handleSuccess
            )
        }
    }

    private fun handleSuccess(list: List<Vacancy>) {
        log(thisName, "handleSuccess(list: $list)")
        _uiState.value = SimilarVacanciesState.Content(list)
    }

    override fun handleFailure(failure: Failure) {
        log(thisName, "handleFailure(failure: $failure)")
        when (failure) {
            is Failure.Offline -> {
                _uiState.value = SimilarVacanciesState.Offline(failure)
            }
            is Failure.NotFound -> {
                _uiState.value = SimilarVacanciesState.Empty
            }
            else -> {
                _uiState.value = SimilarVacanciesState.Error(failure)
            }
        }
    }
}