package ru.practicum.android.diploma.favourites.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favourites.domain.api.FavouritesInteractor

class FavouritesViewModel(private val favouritesInteractor: FavouritesInteractor) : ViewModel() {

    private val _screenState = MutableLiveData<FavouritesState>(FavouritesState.Loading)
    val screenState: LiveData<FavouritesState> = _screenState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            favouritesInteractor.favouriteVacancies().collect { vacancyList ->
                _screenState.postValue(FavouritesState.Content(vacancyList))
            }
        }
    }

}
