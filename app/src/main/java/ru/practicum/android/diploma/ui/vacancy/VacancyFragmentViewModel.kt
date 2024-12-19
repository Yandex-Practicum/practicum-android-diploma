package ru.practicum.android.diploma.ui.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.SingleLiveEvent

class VacancyFragmentViewModel(private val vacancyInteractor: VacancyInteractor) : ViewModel() {

    private val shareState = SingleLiveEvent<ShareData>()
    fun observeShareState(): LiveData<ShareData> = shareState

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun shareVacancy(id: String) {
        shareState.postValue(vacancyInteractor.getShareData(id))
    }

    fun isVacancyInFavorites(id: String) {
        viewModelScope.launch {
            _isFavorite.postValue(vacancyInteractor.isFavorite(id))
        }
    }

    fun onFavoriteClicked(vacancy: Vacancy) {
        viewModelScope.launch {
            if (_isFavorite.value == true) {
                vacancyInteractor.deleteFavouritesVacancyEntity(vacancy)
                _isFavorite.postValue(false)
            } else {
                vacancyInteractor.addVacancyToFavorites(vacancy)
                _isFavorite.postValue(true)
            }
        }
    }
}
