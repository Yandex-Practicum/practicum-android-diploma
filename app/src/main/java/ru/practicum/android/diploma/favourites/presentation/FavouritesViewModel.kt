package ru.practicum.android.diploma.favourites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.favourites.domain.api.GetFavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.models.FavouritesState

class FavouritesViewModel(private val getFavouritesInteractor: GetFavouritesInteractor): ViewModel() {

    private var _favouritesListMutable = MutableLiveData<List<DetailVacancy>>()
    val _favouritesList: LiveData<List<DetailVacancy>> = _favouritesListMutable

    private var _favouritesStatusMutable = MutableLiveData<FavouritesState>()
    val favouritesStatus: LiveData<FavouritesState> = _favouritesStatusMutable

    fun getFavouritesList() {
        viewModelScope.launch {
            getFavouritesInteractor.getFavouritesList().collect { it ->
                _favouritesListMutable.postValue(listOf<DetailVacancy>())
                if (it == null) {
                    _favouritesStatusMutable.postValue(FavouritesState.ERROR)
                } else if (it.isEmpty()) {
                    _favouritesStatusMutable.postValue(FavouritesState.EMPTY_RESULT)
                } else {
                    _favouritesListMutable.postValue(it!!)
                    _favouritesStatusMutable.postValue(FavouritesState.SUCCESS)

                }
            }
        }
    }
}
