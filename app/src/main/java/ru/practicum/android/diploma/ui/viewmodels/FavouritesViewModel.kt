package ru.practicum.android.diploma.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavoritesInteractor

class FavouritesViewModel(private val favoritesInteractor: FavoritesInteractor) : ViewModel() {
    private val _favoritesViewState = MutableLiveData<FavoritesState>(FavoritesState.Loading)
    fun favoritesViewState(): LiveData<FavoritesState> = _favoritesViewState

    private fun fetchFavorites() {
        viewModelScope.launch {
            favoritesInteractor.getFavoriteVacancies().catch {
                _favoritesViewState.postValue(FavoritesState.Error)
            }.collect { data ->
                if (data.isEmpty()) {
                    _favoritesViewState.postValue(FavoritesState.Empty)
                } else {
                    _favoritesViewState.postValue(FavoritesState.Content(data))
                }
            }
        }
    }
}
