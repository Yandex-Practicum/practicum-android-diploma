package ru.practicum.android.diploma.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoriteVacanciesViewModel : ViewModel() {

    private var _state = MutableLiveData<FavoriteVacanciesState>(
        FavoriteVacanciesState.EmptyVacancyList
    )

    fun getState(): LiveData<FavoriteVacanciesState> = _state
}
