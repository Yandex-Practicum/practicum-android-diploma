package ru.practicum.android.diploma.details.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.details.domain.AddToFavoriteUseCase
import ru.practicum.android.diploma.details.domain.GetFavoriteVacanciesUseCase
import ru.practicum.android.diploma.details.domain.RemoveVacancyFromFavoritesUseCase
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.search.domain.Vacancy
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    logger: Logger,
    private val addUseCase: AddToFavoriteUseCase,
    private val removeVacancyFromFavoriteUseCase: RemoveVacancyFromFavoritesUseCase,
    getFavoriteVaccanciesUseCase: GetFavoriteVacanciesUseCase
) : BaseViewModel(logger) {
    val favs = getFavoriteVaccanciesUseCase.invoke()


    fun addToFavorites(vacancy: Vacancy){
        viewModelScope.launch(Dispatchers.IO) {
            log(thisName, "addToFavorites   }")
            addUseCase.invoke(vacancy).collect(){
                log(thisName, "id inserted= $it")
            }
        }
    }
    fun deleteVacancy(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            removeVacancyFromFavoriteUseCase.invoke(id).collect(){
                log(thisName,"$id was removed")
            }
        }
    }

}