package ru.practicum.android.diploma.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.detail.DetailInteractor
import ru.practicum.android.diploma.domain.favorite.FavouriteInteractor
import ru.practicum.android.diploma.domain.models.detail.FullVacancy

class DetailViewModel(
    val interactor: DetailInteractor,
    val favouriteInteractor: FavouriteInteractor
) : ViewModel() {

    private var favouriteStateLiveData = MutableLiveData<Boolean>()
    fun observedFavouriteState(): LiveData<Boolean> = favouriteStateLiveData
    fun getVacancy(): FullVacancy {
        return interactor.getVacancy()
    }

    fun addToFavourite(fullVacancy: FullVacancy) {
        viewModelScope.launch {
            favouriteInteractor.addToFavourite(fullVacancy)
                .collect() {
                    renderState(it)
                }
        }
    }

    private fun renderState(isAdded: Boolean) {
        favouriteStateLiveData.postValue(isAdded)
    }
}
