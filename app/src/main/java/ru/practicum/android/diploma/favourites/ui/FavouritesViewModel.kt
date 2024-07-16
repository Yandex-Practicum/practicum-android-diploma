package ru.practicum.android.diploma.favourites.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favourites.domain.api.FavouritesInteractor
import java.sql.SQLException

class FavouritesViewModel(private val favouritesInteractor: FavouritesInteractor) : ViewModel() {

    private val _screenState = MutableLiveData<FavouritesState>(FavouritesState.Loading)
    val screenState: LiveData<FavouritesState> = _screenState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favouritesInteractor.favouriteVacancies().collect { vacancyList ->
                    _screenState.postValue(FavouritesState.Content(vacancyList))
                }
            } catch (e: SQLException) {
                Log.e("SQLException", e.toString())
                _screenState.postValue(FavouritesState.Error)
            }

        }
    }

}
