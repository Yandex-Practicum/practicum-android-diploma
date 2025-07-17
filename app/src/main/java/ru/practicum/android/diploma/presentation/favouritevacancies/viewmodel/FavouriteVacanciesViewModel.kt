package ru.practicum.android.diploma.presentation.favouritevacancies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.favouritevacancies.usecases.FavouriteVacanciesDbInteractor
import ru.practicum.android.diploma.presentation.favouritevacancies.uistate.FavouriteVacanciesUiState
import ru.practicum.android.diploma.presentation.favouritevacancies.uistate.FavouriteVacancyDetailsUiState
import ru.practicum.android.diploma.presentation.mappers.toFavouriteUi
import ru.practicum.android.diploma.presentation.mappers.toUiModel

class FavouriteVacanciesViewModel(
    private val favouriteVacanciesDbInteractor: FavouriteVacanciesDbInteractor,
) : ViewModel() {
    private val _favouriteUiState = MutableLiveData<FavouriteVacanciesUiState>()
    val favouriteUiState: MutableLiveData<FavouriteVacanciesUiState> = _favouriteUiState

    private val _vacancyDetailsUiState = MutableLiveData<FavouriteVacancyDetailsUiState>()
    val vacancyDetailsUiState: MutableLiveData<FavouriteVacancyDetailsUiState> = _vacancyDetailsUiState

    fun refreshVacancies() {
        viewModelScope.launch {
            favouriteVacanciesDbInteractor.getFavouriteVacancies()
                .collect { vacanciesList ->
                    val uiList = vacanciesList.map { vacancy -> vacancy.toUiModel() }
                    _favouriteUiState.postValue(FavouriteVacanciesUiState.Content(uiList))
                }
        }
    }

    fun getFavouriteVacancyDetails(id: String) {
        viewModelScope.launch {
            val vacancy = favouriteVacanciesDbInteractor.getVacancyById(id)
            if (vacancy != null) {
                _vacancyDetailsUiState.postValue(FavouriteVacancyDetailsUiState.Content(vacancy.toFavouriteUi()))
            } else {
                _vacancyDetailsUiState.postValue(FavouriteVacancyDetailsUiState.Error)
            }
        }
    }
}
