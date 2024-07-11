package ru.practicum.android.diploma.vacancy.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favourites.domain.api.FavouritesInteractor
import ru.practicum.android.diploma.sharing.domain.SharingInteractor
import ru.practicum.android.diploma.vacancy.domain.api.VacancyInteractor

class VacancyViewModel(
    private val vacancyInteractor: VacancyInteractor,
    private val favouritesInteractor: FavouritesInteractor,
    private val sharingInteractor: SharingInteractor
) : ViewModel() {

    private val _screenState = MutableLiveData<VacancyState>(VacancyState.Loading)
    val screenState: LiveData<VacancyState> = _screenState
    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite
    private var id = -1

    init {
        viewModelScope.launch {
            favouritesInteractor.favouriteIds().collect { ids ->
                _isFavorite.value = ids.contains(id)
            }
        }
    }

    fun loadVacancy(vacancyId: Int) {
        id = vacancyId
        viewModelScope.launch(Dispatchers.IO) {
            vacancyInteractor.getVacancy(vacancyId).collect { vacancy ->
                _screenState.postValue(VacancyState.Content(vacancy))
            }
        }
    }

    fun changeFavorite() {
        val vacancyFull = (_screenState.value as VacancyState.Content).vacancyFull
        viewModelScope.launch(Dispatchers.IO) {
            favouritesInteractor.changeFavourite(vacancyFull)
        }
    }

    fun shareVacancy() {
        val link = (_screenState.value as VacancyState.Content).vacancyFull.alternateUrl
        sharingInteractor.shareVacancy(link)
    }

    fun sendMessageToEmail() {
        sharingInteractor.sendMessageToEmail("exampleEmail@ya.ru")

    }

    fun callEmployer() {
        sharingInteractor.callEmployer("89999999999")
    }
}
