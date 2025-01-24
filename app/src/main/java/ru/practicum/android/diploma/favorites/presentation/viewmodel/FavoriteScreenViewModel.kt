package ru.practicum.android.diploma.favorites.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.favorites.domain.interactor.FavoriteInteractor
import ru.practicum.android.diploma.favorites.presentation.state.FavoritesScreenState

class FavoriteScreenViewModel(
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {
    private val state = MutableLiveData<FavoritesScreenState>()

    init {
        loadData()
    }

    fun getScreenState(): LiveData<FavoritesScreenState> {
        return state
    }

    fun loadData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                favoriteInteractor.getFavoritesList()
                    .collect() { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }

    private fun processResult(foundTracks: List<Track>?, errorMessage: String?) {
        val tracks = mutableListOf<Track>()
        if (foundTracks != null) {
            tracks.addAll(foundTracks)
        }

        when {
            errorMessage != null -> {
                val error = LikeTracksScreenState.Error(
                    message = context.getString(
                        R.string.your_mediateka_is_empty
                    )
                )
                state.postValue(error)
            }

            else -> {
                val content = LikeTracksScreenState.Content(data = tracks)
                state.postValue(content)
            }
        }
    }
}
