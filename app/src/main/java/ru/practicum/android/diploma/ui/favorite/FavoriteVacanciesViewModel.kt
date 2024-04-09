package ru.practicum.android.diploma.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails

class FavoriteVacanciesViewModel(
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {

    private var _state = MutableLiveData<FavoriteVacanciesState>(
        FavoriteVacanciesState.EmptyVacancyList
    )

    fun getState(): LiveData<FavoriteVacanciesState> = _state

    fun reloadFavoriteVacancies() {
        viewModelScope.launch {
            val data = ArrayList<VacancyDetails>()
            favoriteInteractor.getAllFavoriteVacancies().collect {
                data.add(it)
            }
            if (data.size > 0) {
                _state.postValue(FavoriteVacanciesState.VacancyList(data))
            } else {
                _state.postValue(FavoriteVacanciesState.EmptyVacancyList)
            }
        }
    }
}
