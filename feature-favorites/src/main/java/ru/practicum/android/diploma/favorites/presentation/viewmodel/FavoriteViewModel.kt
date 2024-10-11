package ru.practicum.android.diploma.favorites.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.domain.model.FavoriteVacancy
import ru.practicum.android.diploma.favorites.domain.usecase.FavoriteInteractor
import ru.practicum.android.diploma.favorites.presentation.viewmodel.state.FavoriteState

private const val PAGE_SIZE = 20
internal class FavoriteViewModel(
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {

    private var currentPage = 0
    private var favoriteState: FavoriteState = FavoriteState.Empty
    private var vacanciesNumber = -1
    private val vacancies = mutableListOf<FavoriteVacancy>()

    private var favoriteStateLiveData = MutableLiveData<FavoriteState>()
    fun getFavoriteStateLiveData(): LiveData<FavoriteState> = favoriteStateLiveData

    fun getVacanciesNumberAndInitFirstList() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteInteractor.getVacanciesNumber().collect { pair ->
                val number = pair.first
                val error = pair.second
                favoriteState = vacanciesNumberResultAndInitFirstList(number, error)
                favoriteStateLiveData.postValue(favoriteState)
            }
        }
    }

    fun getVacanciesPaginated() {
        val startPage = currentPage * PAGE_SIZE
        if (favoriteState !is FavoriteState.Error && startPage < vacanciesNumber) {
            viewModelScope.launch(Dispatchers.IO) {
                favoriteInteractor.getVacanciesPaginated(PAGE_SIZE, startPage).collect { pair ->
                    val state = vacanciesPaginatedResult(pair.first, pair.second)
                    currentPage++
                    favoriteStateLiveData.postValue(
                        state
                    )
                }
            }
        }
    }

    fun clearVacancies() {
        currentPage = 0
        favoriteState = FavoriteState.Empty
        vacanciesNumber = -1
        vacancies.clear()
    }

    private fun vacanciesPaginatedResult(vacancyList: List<FavoriteVacancy>?, errorMessage: String?): FavoriteState {
        if (vacancyList != null) {
            vacancies.addAll(vacancyList)
        }
        return when {
            errorMessage != null -> {
                FavoriteState.Error
            }
            vacancies.isEmpty() -> {
                FavoriteState.Empty
            }
            else -> {
                FavoriteState.Content(vacancies)
            }
        }
    }

    private fun vacanciesNumberResultAndInitFirstList(vacancyNumber: Int?, errorMessage: String?): FavoriteState {
        if (vacancyNumber != null) {
            vacanciesNumber = vacancyNumber
        }
        return when {
            errorMessage != null -> {
                FavoriteState.Error
            }
            vacanciesNumber == 0 -> {
                FavoriteState.Empty
            }
            else -> {
                getVacanciesPaginated()
                FavoriteState.Content(vacancies)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        clearVacancies()
    }
}
