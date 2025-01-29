package ru.practicum.android.diploma.favorites.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.favorites.domain.interactor.FavoriteInteractor
import ru.practicum.android.diploma.favorites.presentation.state.FavoritesScreenState
import ru.practicum.android.diploma.search.domain.model.VacancyItems

class FavoriteScreenViewModel(
    private val favoriteInteractor: FavoriteInteractor,
    private val context: Context
) : ViewModel() {
    private val state = MutableLiveData<FavoritesScreenState>()

    init {
        loadData()
    }

    fun getScreenState(): LiveData<FavoritesScreenState> {
        return state
    }

    fun loadData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                favoriteInteractor.getFavoritesList()
                    .collect() { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }

    private fun processResult(foundVacancys: List<VacancyItems>?, errorMessage: String?) {
        val vacancyList = mutableListOf<VacancyItems>()
        if (foundVacancys != null) {
            vacancyList.addAll(foundVacancys)
        }

        when {
            errorMessage != null -> {
                val error = FavoritesScreenState.Error(
                    message = errorMessage
                )
                state.postValue(error)
            }

            else -> {
                val content = FavoritesScreenState.Content(data = vacancyList)
                state.postValue(content)
            }
        }
    }
}
