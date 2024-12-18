package ru.practicum.android.diploma.ui.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.SingleLiveEvent

class VacancyFragmentViewModel(private val vacancyInteractor: VacancyInteractor) : ViewModel() {

    private val shareState = SingleLiveEvent<ShareData>()
    fun observeShareState(): LiveData<ShareData> = shareState

    val favoritesState = MutableLiveData<Boolean>()
    fun observeFavoritesState(): LiveData<Boolean> = favoritesState

    fun shareVacancy(id: String) {
        shareState.postValue(vacancyInteractor.getShareData(id))
    }

    fun isVacancyInFavorites(trackId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesState.postValue(
                vacancyInteractor.isFavorite(trackId)
            )
        }
    }

    fun onFavoriteClicked(vacancy: Vacancy) {
        viewModelScope.launch(Dispatchers.IO) {
            if (vacancy.isFavorite) {
                vacancyInteractor.deleteFavouritesVacancyEntity(vacancy)
                vacancy.isFavorite = false
                favoritesState.postValue(false)
            } else {
                vacancyInteractor.addVacancyToFavorites(vacancy)
                vacancy.isFavorite = true
                favoritesState.postValue(true)
            }
        }
    }
}
