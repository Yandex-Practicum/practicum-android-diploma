package ru.practicum.android.diploma.presentation.favorites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.favorites.FavoritesVacancyViewState
import java.io.IOException
import ru.practicum.android.diploma.domain.favorites.FavoritesVacancyInteractor

class FavoritesViewModel(private val favoritesVacancyInteractor: FavoritesVacancyInteractor) : ViewModel() {
    private val teg = "favorites"
    private val _favoriteVacancyScreenState = MutableLiveData<FavoritesVacancyViewState>()
    val favoriteVacancyScreenState: LiveData<FavoritesVacancyViewState> get() = _favoriteVacancyScreenState
    fun getAllFavoritesVacancy() {
        viewModelScope.launch {
            favoritesVacancyInteractor.getAllFavoritesVacancy().collect {
                try {
                    if (it?.isEmpty() == true) {
                        _favoriteVacancyScreenState.postValue(
                            FavoritesVacancyViewState
                                .FavoritesVacancyEmptyDataResult
                        )
                    } else {
                        _favoriteVacancyScreenState.postValue(
                            it?.let { it1 ->
                                FavoritesVacancyViewState
                                    .FavoritesVacancyDataResult(
                                        listOfFavoriteDomainVacancy = it1
                                    )
                            }
                        )
                    }
                } catch (e: IOException) {
                    Log.e(teg, "Caught exception:  ${e.message}")
                    _favoriteVacancyScreenState.postValue(
                        FavoritesVacancyViewState.FavoritesVacancyError
                    )
                }
            }
        }

    }
}

