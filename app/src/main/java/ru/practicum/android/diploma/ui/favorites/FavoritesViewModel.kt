package ru.practicum.android.diploma.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

sealed class FavoritesState {
    object Loading : FavoritesState()
    data class Content(val vacancies: List<Vacancy>) : FavoritesState()
    object Empty : FavoritesState()
    object Error : FavoritesState()
}

class FavoritesViewModel(
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {

    private val _state = MutableLiveData<FavoritesState>()
    val state: LiveData<FavoritesState> = _state

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _state.value = FavoritesState.Loading
            try {
                val favorites = favoritesInteractor.getAllFavorites()
                _state.value = if (favorites.isEmpty()) {
                    FavoritesState.Empty
                } else {
                    FavoritesState.Content(favorites)
                }
            } catch (_: Exception) {
                _state.value = FavoritesState.Error
            }
        }
    }

    fun refresh() {
        loadFavorites()
    }

    suspend fun isFavorite(vacancyId: String): Boolean {
        return favoritesInteractor.isFavorite(vacancyId)
    }
}
