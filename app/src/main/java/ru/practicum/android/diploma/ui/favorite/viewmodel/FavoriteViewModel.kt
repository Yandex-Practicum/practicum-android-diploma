package ru.practicum.android.diploma.ui.favorite.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.practicum.android.diploma.domain.db.FavoriteInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.ui.favorite.models.FavoriteState

class FavoriteViewModel(
    application: Application,
    favoriteInteractor: FavoriteInteractor
) : AndroidViewModel(application) {
    private val favoriteList = ArrayList<VacancyDetail>()

    private val stateLiveData = MutableLiveData<FavoriteState>()
    fun observeState(): LiveData<FavoriteState> = stateLiveData

    fun getFavoriteList() {
        renderState(FavoriteState.EmptyList)
    }

    private fun renderState(state: FavoriteState) {
        stateLiveData.postValue(state)
    }
}
