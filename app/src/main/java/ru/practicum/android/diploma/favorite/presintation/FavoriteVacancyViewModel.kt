package ru.practicum.android.diploma.favorite.presintation

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.util.SingleEventLiveData

class FavoriteVacancyViewModel(
    private val favoriteInteractor: FavoriteVacancyInteractor
) : ViewModel() {

    private val vacancyClickEvent = SingleEventLiveData<String>()
    fun getVacancyClickEvent(): LiveData<String> = vacancyClickEvent

    private val stateLiveData = MutableLiveData<FavoriteScreenState>()
    fun observeState(): LiveData<FavoriteScreenState> = stateLiveData

    init {
        fillData()
    }
    private fun fillData() {
        viewModelScope.launch {
            try {
                favoriteInteractor
                    .getVacancies()
                    .collect { vacancies ->
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

    fun onVacancyClick(vacancySearch: VacancySearch) {
        vacancyClickEvent.value = vacancySearch.id
    }
}
