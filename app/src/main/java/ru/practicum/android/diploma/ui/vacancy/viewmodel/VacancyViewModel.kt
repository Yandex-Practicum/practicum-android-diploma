package ru.practicum.android.diploma.ui.vacancy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.favorites.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.VacancyInteractor
import ru.practicum.android.diploma.ui.search.state.VacancyError
import ru.practicum.android.diploma.ui.vacancy.VacancyState
import ru.practicum.android.diploma.util.Resource

class VacancyViewModel(
    private val interactor: VacancyInteractor,
    private val favoriteVacanciesInteractor: FavoriteVacanciesInteractor
) : ViewModel() {

    private var currentVacancy: Vacancy? = null
    private var currentVacancyId: String? = null

    private val vacancyScreenStateLiveData = MutableLiveData<VacancyState>()
    private val favoriteVacancyButtonStateLiveData = MutableLiveData<FavoriteVacancyButtonState>()
    private val shareLinkStateLiveData = MutableLiveData<ShareLinkState>()

    val getVacancyScreenStateLiveData: LiveData<VacancyState> = vacancyScreenStateLiveData
    val getFavoriteVacancyButtonStateLiveData: LiveData<FavoriteVacancyButtonState> = favoriteVacancyButtonStateLiveData
    val getShareLinkStateLiveData: LiveData<ShareLinkState> = shareLinkStateLiveData

    fun shareVacancy() {
        shareLinkStateLiveData.postValue(ShareLinkState(currentVacancy?.alternateUrl))
    }

    fun insertVacancyInFavorites() {
        viewModelScope.launch {
            favoriteVacanciesInteractor.insertFavoriteVacancy(currentVacancy!!)
            favoriteVacancyButtonStateLiveData.postValue(FavoriteVacancyButtonState.VacancyIsFavorite)
        }
    }

    fun deleteVacancyFromFavorites() {
        viewModelScope.launch {
            favoriteVacanciesInteractor.deleteFavoriteVacancy(currentVacancy!!.id)
            favoriteVacancyButtonStateLiveData.postValue(FavoriteVacancyButtonState.VacancyIsNotFavorite)
        }
    }

    fun getVacancyResources(id: String) {
        renderState(VacancyState.Loading)

        currentVacancyId = id

        viewModelScope.launch {
            interactor.getVacancyId(id).collect { vacancy ->
                processResult(vacancy)
            }
        }
    }

    private fun processResult(resource: Resource<Vacancy>) {
        when (resource) {
            is Resource.Success -> {
                currentVacancy = resource.data
                renderState(VacancyState.Content(currentVacancy!!))
                viewModelScope.launch {
                    favoriteVacanciesInteractor
                        .getFavoriteVacancyById(currentVacancyId!!)
                        .collect { foundedVacancy ->
                            favoriteVacancyButtonStateLiveData.postValue(
                                if (foundedVacancy == null) {
                                    FavoriteVacancyButtonState.VacancyIsNotFavorite
                                } else {
                                    FavoriteVacancyButtonState.VacancyIsFavorite
                                }
                            )
                        }
                }
            }

            is Resource.Error -> {
                getFavoriteVacancyFromDb(currentVacancyId!!, resource.message)
            }
        }
    }

    private fun renderErrorState(error: VacancyError) {
        when (error) {
            VacancyError.NETWORK_ERROR -> {
                renderState(VacancyState.NetworkError)
            }

            VacancyError.BAD_REQUEST -> {
                renderState(VacancyState.BadRequest)
            }

            VacancyError.NOT_FOUND -> {
                renderState(VacancyState.Empty)
            }

            VacancyError.UNKNOWN_ERROR, VacancyError.SERVER_ERROR -> {
                renderState(VacancyState.ServerError)
            }
        }
    }

    private fun getFavoriteVacancyFromDb(vacancyId: String, error: VacancyError) {
        viewModelScope.launch {
            favoriteVacanciesInteractor
                .getFavoriteVacancyById(vacancyId)
                .collect { foundedVacancy ->
                    if (foundedVacancy != null) {
                        currentVacancy = foundedVacancy
                        favoriteVacancyButtonStateLiveData.postValue(FavoriteVacancyButtonState.VacancyIsFavorite)
                        renderState(VacancyState.Content(foundedVacancy))
                    } else {
                        renderErrorState(error)
                    }
                }
        }
    }

    private fun renderState(state: VacancyState) {
        vacancyScreenStateLiveData.postValue(state)
    }

}
