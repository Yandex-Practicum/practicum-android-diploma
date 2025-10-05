package ru.practicum.android.diploma.ui.vmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.database.dao.VacancyDao
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.models.SearchState

class MainViewModel(
    private val interactor: FavoritesInteractor
) : ViewModel() {
    private val _searchState = MutableLiveData<SearchState>(SearchState.Idle)
    val observeSearchState: LiveData<SearchState> = _searchState
    init {
        viewModelScope.launch {
            interactor.setVacancy(
                Vacancy(
                    2,"afasf","asfasdfaef", Salary(null,null,"afafdsd"), Employer("as","aaf","afaf"),
                    Area("afaf","adfafdsd")

                )
            )
            interactor.checkVacancyInFavorite(2).collect{
                Log.v("my","view model  $it")
            }
        }
    }
    fun setNothingState() {
        _searchState.value = SearchState.Nothing
    }

    fun setIdleState() {
        _searchState.value = SearchState.Idle
    }

    fun search(value: String) {
        _searchState.value = SearchState.Loading
    }
}
