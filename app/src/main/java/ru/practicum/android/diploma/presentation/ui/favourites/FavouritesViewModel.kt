package ru.practicum.android.diploma.presentation.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.FavouritesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

class FavouritesViewModel(
    private val favouritesInteractor: FavouritesInteractor
) : ViewModel() {
    private val _stateLiveData = MutableLiveData<FavouriteState>()
    fun observeState(): LiveData<FavouriteState> = _stateLiveData

    fun loadVacancy() {
        viewModelScope.launch {
            favouritesInteractor
                .getFavouritesVacancy()
                .collect { vacancy ->
                    processResult(vacancy)
                }
        }
    }

    private fun processResult(vacancy: List<Vacancy>) {
        if (vacancy.isEmpty()) {
            _stateLiveData.postValue(FavouriteState.Empty(R.string.list_is_empty))
        } else {
            _stateLiveData.postValue(FavouriteState.Content(vacancy))
        }
    }
}
