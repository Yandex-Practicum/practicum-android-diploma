package ru.practicum.android.diploma.details.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.details.domain.DetailsInteractor
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    logger: Logger,
    private val detailsInteractor: DetailsInteractor
) : BaseViewModel(logger) {

    fun addToFavorites(vacancy: Vacancy){
        viewModelScope.launch(Dispatchers.IO) {
            log(thisName, "addToFavorites   }")
            detailsInteractor.addVacancyToFavorites(vacancy).collect{
                log(thisName, "id inserted= $it")
            }
        }
    }
    fun deleteVacancy(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            detailsInteractor.removeVacancyFromFavorite(id).collect{
                log(thisName,"$id was removed")
            }
        }
    }

}