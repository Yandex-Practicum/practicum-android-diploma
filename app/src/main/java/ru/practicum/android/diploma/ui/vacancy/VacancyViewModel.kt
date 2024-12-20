package ru.practicum.android.diploma.ui.vacancy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.SingleLiveEvent
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto
import ru.practicum.android.diploma.domain.vacancy.VacancyInteractor

class VacancyViewModel(
    application: Application,
    private val interactor: VacancyInteractor
) : AndroidViewModel(application) {

    private val shareState = SingleLiveEvent<ShareData>()
    fun observeShareState(): LiveData<ShareData> = shareState
    private val vacancyScreenStateLiveData = MutableLiveData<VacancyState>()
    private var listVacancy: VacancyFullItemDto? = null

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite
    val getVacancyScreenStateLiveData: LiveData<VacancyState> = vacancyScreenStateLiveData

    fun shareVacancy(id: String) {
        shareState.postValue(interactor.getShareData(id))
    }

    fun getVacancyRessurces(id: String) {
        renderState(VacancyState.Loading)
        viewModelScope.launch {
            _isFavorite.postValue(interactor.isFavorite(id))
            interactor.getVacancyId(id).collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun processResult(vacancy: VacancyFullItemDto?, errorMessage: String?) {
        if (vacancy != null) {
            listVacancy = vacancy
            renderState(VacancyState.Content(listVacancy!!))
        }

        when (errorMessage) {
            "Network Error" -> {
                renderState(VacancyState.NetworkError)
            }

            "Bad Request" -> {
                renderState(VacancyState.BadRequest)
            }

            "Not Found" -> {
                renderState(VacancyState.Empty)
            }

            "Unknown Error" -> {
                renderState(VacancyState.ServerError)
            }

            "Server Error" -> {
                renderState(VacancyState.ServerError)
            }
        }
    }

    fun onFavoriteClicked(vacancy: Vacancy) {
        viewModelScope.launch {
            if (_isFavorite.value == true) {
                interactor.deleteFavouritesVacancyEntity(vacancy)
                _isFavorite.postValue(false)
            } else {
                interactor.addVacancyToFavorites(vacancy)
                _isFavorite.postValue(true)

            }
        }
    }

    fun isVacancyInFavorites(id: String) {
        viewModelScope.launch {
            _isFavorite.postValue(interactor.isFavorite(id))
        }
    }

    private fun renderState(state: VacancyState) {
        vacancyScreenStateLiveData.postValue(state)
    }
}
