package ru.practicum.android.diploma.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.VacancyInteractor

class FavoritesViewModel(
    application: Application,
    private val interactor: VacancyInteractor
) : AndroidViewModel(application) {

    private val favoriteVacancies = MutableLiveData<List<Vacancy>>()
    fun getFavoriteTracks(): LiveData<List<Vacancy>> = favoriteVacancies

    fun returnFavoriteTracks() {
        viewModelScope.launch {
            interactor.getFavoritesVacancies().collect { trackList ->
                favoriteVacancies.postValue(trackList)
            }
        }
    }

    init {
        returnFavoriteTracks()
    }


}
