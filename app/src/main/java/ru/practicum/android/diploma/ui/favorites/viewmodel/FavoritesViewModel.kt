package ru.practicum.android.diploma.ui.favorites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.favorites.FavoriteVacanciesInteractor

class FavoritesViewModel(
    private val favoriteVacanciesInteractor: FavoriteVacanciesInteractor
) : ViewModel() {

    private val favoriteVacanciesScreenStateLiveData = MutableLiveData<FavoriteVacanciesScreenState>()

    fun getFavoriteVacanciesScreenStateLiveData(): LiveData<FavoriteVacanciesScreenState> =
        favoriteVacanciesScreenStateLiveData

    fun getFavoriteVacanciesList() {
        viewModelScope.launch {
            favoriteVacanciesInteractor.getFavoriteVacancies().collect { listOfFavoriteVacancies ->
                if (listOfFavoriteVacancies == null) {
                    renderState(FavoriteVacanciesScreenState.DbError)
                } else if (listOfFavoriteVacancies.isEmpty()) {
                    renderState(FavoriteVacanciesScreenState.Empty)
                } else {
                    renderState(FavoriteVacanciesScreenState.Content(listOfFavoriteVacancies))
                }
            }
        }
    }

    private fun renderState(state: FavoriteVacanciesScreenState) {
        favoriteVacanciesScreenStateLiveData.postValue(
            when (state) {
                is FavoriteVacanciesScreenState.Empty -> FavoriteVacanciesScreenState.Empty
                is FavoriteVacanciesScreenState.Content -> FavoriteVacanciesScreenState.Content(state.favoriteVacancies)
                is FavoriteVacanciesScreenState.DbError -> FavoriteVacanciesScreenState.DbError
            }
        )
    }

}
