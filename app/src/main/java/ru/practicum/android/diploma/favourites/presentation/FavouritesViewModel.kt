package ru.practicum.android.diploma.favourites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.favourites.domain.api.GetFavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.models.FavouritesState

const val CLICK_DEBOUNCE_DELAY = 1000L

class FavouritesViewModel(private val getFavouritesInteractor: GetFavouritesInteractor) : ViewModel() {

    private var favouritesListMutable = MutableLiveData<List<DetailVacancy>>()
    val favouritesList: LiveData<List<DetailVacancy>> = favouritesListMutable

    private var favouritesStatusMutable = MutableLiveData<FavouritesState>()
    val favouritesStatus: LiveData<FavouritesState> = favouritesStatusMutable

    private var isClickAllowed = true

    fun getFavouritesList() {
        fillVacancyList()

        viewModelScope.launch {
            getFavouritesInteractor.getFavouritesList().collect {
                favouritesListMutable.postValue(listOf<DetailVacancy>())
                if (it == null) {
                    favouritesStatusMutable.postValue(FavouritesState.ERROR)
                } else if (it.isEmpty()) {
                    favouritesStatusMutable.postValue(FavouritesState.EMPTY_RESULT)
                } else {
                    favouritesListMutable.postValue(it!!)
                    favouritesStatusMutable.postValue(FavouritesState.SUCCESS)
                }
            }
        }
    }

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

    private fun fillVacancyList() {
        val mockList = mutableListOf<DetailVacancy>()
        repeat('0'.code) {
            val vac = DetailVacancy(
                it.toLong(),
                "Андроид-разработчик",
                "100 000",
                "",
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
                "Еда",
                "Москва",
                ""
            )
            mockList.add(vac)
        }

        viewModelScope.launch {
            mockList.forEach {
                getFavouritesInteractor.fillVacList(it)
            }
        }
    }
}
