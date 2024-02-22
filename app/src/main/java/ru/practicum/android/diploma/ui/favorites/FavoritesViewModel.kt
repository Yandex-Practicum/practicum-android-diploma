package ru.practicum.android.diploma.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.domain.model.VacancyModel

class FavoritesViewModel(
    val favoriteInteractor: FavoriteInteractor
) : ViewModel() {

    private val _stateLiveData = MutableLiveData<FavoritesState>()
    private val stateLiveData: LiveData<FavoritesState> = _stateLiveData

    fun getState(): LiveData<FavoritesState> = stateLiveData

    init {
        fillData()
    }

    private fun fillData() {
        _stateLiveData.postValue(FavoritesState.Loading)
        getFavorites()
    }

    fun getFavorites() {
        viewModelScope.launch {
            favoriteInteractor.getMockResults().collect(::renderState)
        }
    }

    private fun renderState(vacancies: ArrayList<VacancyModel>) {
        if (vacancies.isEmpty()) {
            _stateLiveData.postValue(FavoritesState.Empty)
        } else {
            _stateLiveData.postValue(FavoritesState.Content(vacancies))
        }

    }
}
