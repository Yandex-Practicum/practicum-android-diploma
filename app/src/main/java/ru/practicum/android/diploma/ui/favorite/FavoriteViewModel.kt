package ru.practicum.android.diploma.ui.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryInteractor
import ru.practicum.android.diploma.domain.api.favorite.FavoritesInteractor
import java.io.IOException

class FavoriteViewModel(
    private val favoritesInteractor: FavoritesInteractor,
    private val dictionaryInteractor: DictionaryInteractor
) : ViewModel() {

    private val _stateFavorite = MutableLiveData<FavoriteState>(FavoriteState.Empty)
    val stateFavorite: LiveData<FavoriteState> get() = _stateFavorite

    fun fillData() {
        renderState(FavoriteState.Loading)
        viewModelScope.launch {
            try {
                val vacancyList = favoritesInteractor.getAllFavorites()
                val currencyDictionary = dictionaryInteractor.getCurrencyDictionary()
                if (vacancyList.isEmpty()) {
                    renderState(FavoriteState.Empty)
                } else {
                    renderState(FavoriteState.Content(vacancyList, currencyDictionary))
                }
            } catch (e: IOException) {
                Log.e("DATA ERROR", e.toString())
                renderState(FavoriteState.Error)
            }
        }
    }

    private fun renderState(state: FavoriteState) {
        _stateFavorite.value = state
    }
}
