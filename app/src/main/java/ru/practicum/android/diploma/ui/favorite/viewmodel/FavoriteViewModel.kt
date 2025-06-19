package ru.practicum.android.diploma.ui.favorite.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.db.FavoriteInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.ui.favorite.models.FavoriteState

class FavoriteViewModel(
    val favoriteInteractor: FavoriteInteractor
) : ViewModel() {
    private val favoriteList = ArrayList<VacancyDetails>()

    private val stateLiveData = MutableLiveData<FavoriteState>()
    fun observeState(): LiveData<FavoriteState> = stateLiveData

    fun getFavoriteList() {
        renderState(FavoriteState.Loading)
        favoriteList.clear()
        viewModelScope.launch {
            favoriteInteractor.getFavorites()
                .collect { list ->
                    Log.d("HH_TEST", "List: $list")
                    favoriteList.addAll(list)
                }
            if (favoriteList.isEmpty()) {
                renderState(FavoriteState.EmptyList)
            } else {
                renderState(FavoriteState.Context(favoriteList))
            }
        }

    }

    private fun renderState(state: FavoriteState) {
        stateLiveData.postValue(state)
    }
}
