package ru.practicum.android.diploma.presentation.favouritevacancies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.favouritevacancies.usecases.FavouriteVacanciesDbInteractor
import ru.practicum.android.diploma.presentation.favouritevacancies.uistate.FavouriteVacanciesUiState
import ru.practicum.android.diploma.presentation.mappers.toUiModel

class FavouriteVacanciesViewModel(
    private val favouriteVacanciesDbInteractor: FavouriteVacanciesDbInteractor
) : ViewModel() {
    private val _favouriteUiState = MutableLiveData<FavouriteVacanciesUiState>()
    val favouriteUiState: MutableLiveData<FavouriteVacanciesUiState> = _favouriteUiState

    fun refreshVacancies() {
        viewModelScope.launch {
            favouriteVacanciesDbInteractor.getFavouriteVacancies()
                .collect { vacanciesList ->
                    val uiList = vacanciesList.map { vacancy -> vacancy.toUiModel() }

                    if (uiList.isEmpty()) {
                        _favouriteUiState.postValue(
                            FavouriteVacanciesUiState.Placeholder(
                                R.drawable.empty_list_favorites_placeholder,
                                R.string.empty_list
                            )
                        )
                    } else {
                        _favouriteUiState.postValue(
                            FavouriteVacanciesUiState.Content(uiList)
                        )
                    }
                }
        }
    }
}
