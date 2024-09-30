package ru.practicum.android.diploma.favorites.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.domain.usecase.FavoriteInteractor
import ru.practicum.android.diploma.favorites.presentation.viewmodel.state.FavoriteState

class FavoriteViewModel(
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {

    private var favoriteStateLiveData = MutableLiveData<FavoriteState>()
    fun getFavoriteStateLiveData(): LiveData<FavoriteState> = favoriteStateLiveData

    fun getVacancies() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteInteractor.getVacancies().collect { favoriteVacancies ->
                when {
                    favoriteVacancies.isEmpty() -> {
                        favoriteStateLiveData.postValue(FavoriteState.Empty)
                    }
                    favoriteVacancies.size < Int.MAX_VALUE -> {
                        favoriteStateLiveData.postValue(
                            FavoriteState.Content(favoriteVacancies)
                        )
                    }
                    else -> {
                        favoriteStateLiveData.postValue(FavoriteState.Error)
                    }
                }
            }
        }
    }
}
