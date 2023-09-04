package ru.practicum.android.diploma.favourite.presentation.viewvodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.db.domain.api.VacancyDbInteractor
import ru.practicum.android.diploma.favourite.presentation.models.FavoriteStateInterface
import ru.practicum.android.diploma.search.domain.models.Vacancy

class FavouriteViewModel(private val favouriteVacancyDbInteractor: VacancyDbInteractor) :
    ViewModel() {

    init {
        showFavouriteVacancies()
    }

    private val stateLiveDataFavourite = MutableLiveData<FavoriteStateInterface>()

    fun observeStateFavourite(): LiveData<FavoriteStateInterface> = stateLiveDataFavourite

    private fun renderStateFavourite(state: FavoriteStateInterface) {
        stateLiveDataFavourite.postValue(state)
    }

    private fun showFavouriteVacancies() {
        viewModelScope.launch {
            val favouriteVacancies = favouriteVacancyDbInteractor.getFavouriteVacancy()

            if (favouriteVacancies.isEmpty()) renderStateFavourite(FavoriteStateInterface.FavoriteVacanciesIsEmpty)
            else renderStateFavourite(FavoriteStateInterface.FavoriteVacancies(favouriteVacancies))
        }
    }

    fun deleteTrack(vacancy: Vacancy) {
        viewModelScope.launch {
            favouriteVacancyDbInteractor.deleteVacancy(vacancy)
        }
    }
}