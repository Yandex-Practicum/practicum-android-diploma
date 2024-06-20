package ru.practicum.android.diploma.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.favorites.FavoritesVacancyViewState

class FavoritesViewModel : ViewModel() {
    // (private val favoritesVacancyInteractor: FavoritesVacancyInteractor)
    private val _favoriteVacancyScreenState = MutableLiveData<FavoritesVacancyViewState>()
    val favoriteVacancyScreenState: LiveData<FavoritesVacancyViewState> get() = _favoriteVacancyScreenState
    fun getAllFavoritesVacancy() {
        /*       viewModelScope.launch {
                    favoritesVacancyInteractor.getAllFavoritesVacancy().collect {
                        try {
                            if (it.isEmpty()) {
                                _favoriteVacancyScreenState.postValue(
                                    FavoritesVacancyViewState
                                        .FavoritesVacancyEmptyDataResult
                                )
                            } else {
                                _favoriteVacancyScreenState.postValue(
                                    FavoritesVacancyViewState
                                        .FavoritesVacancyDataResult(
                                            listOfFavoriteVacancy = it
                                        )
                                )
                            }
                        } catch (e: Exception) {
                            _favoriteVacancyScreenState.postValue(
                                FavoritesVacancyViewState.FavoritesVacancyError
                            )
                        }
                    }
                }
        */
    }
}
