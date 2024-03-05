package ru.practicum.android.diploma.favourites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.favourites.domain.api.GetFavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.models.FavouritesState

const val CLICK_DEBOUNCE_DELAY = 1000L

class FavouritesViewModel(private val getFavouritesInteractor: GetFavouritesInteractor) : ViewModel() {

    var favouritesListMutable = MutableLiveData<List<DetailVacancy>>()

    private var favouritesStatusMutable = MutableLiveData<FavouritesState>()
    val favouritesStatus: LiveData<FavouritesState> = favouritesStatusMutable

    private var isClickAllowed = true

    fun getFavouritesList() {
        favouritesListMutable.value = listOf()
        viewModelScope.launch {
            getFavouritesInteractor.getFavouritesList().collect {
                if (it == null) {
                    favouritesStatusMutable.postValue(FavouritesState.ERROR)
                } else if (it.isEmpty()) {
                    favouritesStatusMutable.postValue(FavouritesState.EMPTY_RESULT)
                } else {
                    favouritesListMutable.value = it
                    favouritesStatusMutable.postValue(FavouritesState.SUCCESS)
                }
            }
        }
    }

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }
}
