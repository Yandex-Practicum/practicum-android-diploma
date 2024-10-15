package ru.practicum.android.diploma.favorite.presentation

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.diploma.search.domain.models.VacancySearch

class FavoriteVacancyViewModel(
    private val favoriteInteractor: FavoriteVacancyInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<FavoriteScreenState>()
    fun observeState(): LiveData<FavoriteScreenState> = stateLiveData

    init {
        updateFavoriteList()
    }

    private fun updateFavoriteList() {
        viewModelScope.launch {
            try {
                favoriteInteractor
                    .getVacancies()
                    .collect {
                        val vacancies = it.reversed()
                        processResult(vacancies)
                    }
            } catch (e: SQLiteException) {
                renderState(FavoriteScreenState.ErrorState)
                println(e)
            }
        }
    }

    private fun processResult(vacancies: List<VacancySearch>) {
        if (vacancies.isEmpty()) {
            renderState(FavoriteScreenState.EmptyState)
        } else {
            renderState(FavoriteScreenState.ContentState(vacancies))
        }
    }

    private fun renderState(state: FavoriteScreenState) {
        stateLiveData.postValue(state)
    }
}
