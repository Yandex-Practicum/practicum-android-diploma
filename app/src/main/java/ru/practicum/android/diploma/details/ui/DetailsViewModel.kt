package ru.practicum.android.diploma.details.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.details.domain.DetailsInteractor
import ru.practicum.android.diploma.filter.data.model.NetworkResponse
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    logger: Logger,
    private val detailsInteractor: DetailsInteractor
) : BaseViewModel(logger) {

    private val _uiState = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Empty)
    val uiState: StateFlow<DetailsScreenState> = _uiState

    private var isInFavorites = false

    fun handleAddToFavsButton(vacancy: Vacancy){
        isInFavorites = !isInFavorites
        val message = when (isInFavorites) {
            true -> {
                addToFavorites(vacancy)
                "vacancy added to favs"
            }
            else -> {
                deleteVacancy(vacancy.id)
                "vacancy removed from favs"
            }
        }
        log(thisName, "handleAddToFavsButton $message")
        _uiState.value = DetailsScreenState.PlayHeartAnimation(isInFavorites, viewModelScope)
    }

   private fun addToFavorites(vacancy: Vacancy) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsInteractor.addVacancyToFavorites(vacancy).collect {
                log("DetailsViewModel", "${vacancy.id} inserted")
            }
        }
    }

    private fun deleteVacancy(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsInteractor.removeVacancyFromFavorite(id).collect {
                log("DetailsViewModel", "$id was removed")
            }
        }
    }

    fun getVacancyByID(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsInteractor.getFullVacancyInfo(id).collect { result ->
                when (result) {
                    is NetworkResponse.Success -> {
                        log(thisName, "NetworkResponse.Success -> ${result.thisName}")
                        _uiState.value = DetailsScreenState.Content(result.data)
                    }
                    is NetworkResponse.Error -> {
                        log(thisName, "NetworkResponse.Error -> ${result.message}")
                        _uiState.value = DetailsScreenState.Error(result.message)
                    }
                    is NetworkResponse.Offline -> {
                        log(thisName, "NetworkResponse.Offline-> ${result.message}")
                        _uiState.value = DetailsScreenState.Offline(result.message)
                    }
                    is NetworkResponse.NoData -> {
                        log(thisName, "NetworkResponse.NoData -> ${result.message}")
                      //  _uiState.value = DetailsScreenState.NoData(result.message)
                    }
                }
            }
        }
    }

}