package ru.practicum.android.diploma.presentation.vacancydetailsscreen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.mappers.toVacancy
import ru.practicum.android.diploma.data.vacancysearchscreen.impl.ErrorType
import ru.practicum.android.diploma.domain.favouritevacancies.usecases.FavouriteVacanciesDbInteractor
import ru.practicum.android.diploma.domain.models.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.vacancydetails.VacancyDetails
import ru.practicum.android.diploma.domain.sharing.SharingInteractor
import ru.practicum.android.diploma.presentation.vacancydetailsscreen.uistate.VacancyDetailsUiState
import ru.practicum.android.diploma.util.Resource

class VacancyDetailsViewModel(
    private val vacancyId: String,
    private val favouriteVacanciesDbInteractor: FavouriteVacanciesDbInteractor,
    private val sharingInteractor: SharingInteractor,
    private val vacancyInteractor: VacanciesInteractor
) : ViewModel() {

    private val _vacancyDetailsState = MutableLiveData<VacancyDetailsUiState>()
    val getVacancyDetailsState: LiveData<VacancyDetailsUiState> = _vacancyDetailsState

    private val _isFavouriteVacancy = MutableLiveData<Boolean>()
    val getIsFavouriteVacancy: LiveData<Boolean> = _isFavouriteVacancy

    init {
        getVacancyDetails()
        checkIfFavourite()
    }

    private fun getVacancyDetails() {
        viewModelScope.launch {
            _vacancyDetailsState.postValue(VacancyDetailsUiState.Loading)
            vacancyInteractor.getVacancyDetailsById(vacancyId)
                .collect {
                    statusVacancy(it)
                }
        }
    }

    private fun statusVacancy(resource: Resource<VacancyDetails>) {
        _vacancyDetailsState.postValue(
            when (resource) {
                is Resource.Error -> {
                    when (resource.errorType) {
                        ErrorType.NO_INTERNET -> VacancyDetailsUiState.NothingFound
                        else -> VacancyDetailsUiState.ServerError
                    }
                }

                is Resource.Success -> {
                    if (resource.data == null) {
                        VacancyDetailsUiState.NothingFound
                    } else {
                        VacancyDetailsUiState.Content(resource.data)
                    }
                }
            }
        )
    }

    private fun checkIfFavourite() {
        viewModelScope.launch {
            val isFavourite = favouriteVacanciesDbInteractor.getVacancyById(vacancyId) != null
            _isFavouriteVacancy.postValue(isFavourite)
        }
    }

    fun shareVacancy(linkVacancy: String) {
        sharingInteractor.shareVacancy(linkVacancy)
    }

    fun onFavouriteClicked() {
        val vacancyDetails = (_vacancyDetailsState.value as? VacancyDetailsUiState.Content)?.data ?: return
        val vacancy = vacancyDetails.toVacancy()

        viewModelScope.launch {
            val isFavourite = favouriteVacanciesDbInteractor.getVacancyById(vacancyId) != null
            if (!isFavourite) {
                favouriteVacanciesDbInteractor.insertVacancy(vacancy)
                _isFavouriteVacancy.value = true
            } else {
                favouriteVacanciesDbInteractor.deleteVacancy(vacancy)
                _isFavouriteVacancy.value = false
            }
        }
    }
}
