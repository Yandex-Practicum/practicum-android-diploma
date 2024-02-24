package ru.practicum.android.diploma.favourites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.core.domain.model.ShortVacancy
import ru.practicum.android.diploma.favourites.data.entity.FavoriteEntity
import ru.practicum.android.diploma.favourites.domain.api.GetFavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.models.FavouritesState

class FavouritesViewModel(private val getFavouritesInteractor: GetFavouritesInteractor): ViewModel() {

    private var _favouritesListMutable = MutableLiveData<List<DetailVacancy>>()
    val _favouritesList: LiveData<List<DetailVacancy>> = _favouritesListMutable

    private var _favouritesStatusMutable = MutableLiveData<FavouritesState>()
    val favouritesStatus: LiveData<FavouritesState> = _favouritesStatusMutable

    fun getFavouritesList() {
        viewModelScope.launch {
            getFavouritesInteractor.getFavouritesList().collect { it ->
                _favouritesListMutable.postValue(listOf<DetailVacancy>())
                if (it == null) {
                    _favouritesStatusMutable.postValue(FavouritesState.ERROR)
                } else if (it.isEmpty()) {
                    _favouritesStatusMutable.postValue(FavouritesState.EMPTY_RESULT)
                } else {
                    _favouritesListMutable.postValue(it!!)
                    _favouritesStatusMutable.postValue(FavouritesState.SUCCESS)

                }
            }
        }
    }


    fun fillVacancyList() {
        val vac1 = DetailVacancy(
            1,
            "Vac 1",
            "100",
            "200",
            "Rub",
            "3 years",
            "Employment",
            "24 on 7",
            "Very rock vacancy",
            listOf(),
            "Anna",
            "ya@ya.ru",
            "_789890980",
            "HR",
            "",
            "PAO MTS",
            "Voronezh"
        )

        val vac2 = DetailVacancy(
            2,
            "Vac 2",
            "300",
            "600",
            "Usd",
            "1 years",
            "Employment 1",
            "always",
            "Very high vacancy",
            listOf(),
            "Anna1",
            "ya@ya1.ru",
            "_7898909888",
            "HR1",
            "",
            "PAO Megafon",
            "Moskva"
        )



        viewModelScope.launch {
            getFavouritesInteractor.fillVacList(vac1)
            getFavouritesInteractor.fillVacList(vac2)
        }
    }
}
