package ru.practicum.android.diploma.details.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.details.domain.DetailsInteractor
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.sharing.domain.api.SharingInteractor
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    logger: Logger,
    private val detailsInteractor: DetailsInteractor,
    private val sharingInteractor: SharingInteractor
) : BaseViewModel(logger) {

    private val _uiState = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Empty)
    val uiState: StateFlow<DetailsScreenState> = _uiState

    private var isInFavorites = false
    private var vacancy: VacancyFullInfo? = null

    fun handleAddToFavsButton(id: String) {
        isInFavorites = !isInFavorites
        val message = when (isInFavorites) {
            true -> {
                vacancy?.let { addToFavorites(it) }
                "vacancy added to favs"
            }
            else -> {
                deleteVacancy(id)
                "vacancy removed from favs"
            }
        }
        log(thisName, "handleAddToFavsButton $message")
        _uiState.value = DetailsScreenState.PlayHeartAnimation(isInFavorites, viewModelScope)
    }

    private fun addToFavorites(vacancy: VacancyFullInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsInteractor.addVacancyToFavorites(vacancy).collect {
                log(thisName, "${vacancy.id} inserted")
            }
        }
    }

    private fun deleteVacancy(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsInteractor.removeVacancyFromFavorite(id).collect {
                log(thisName, "$id deleted")
            }
        }
    }

    fun getFavoriteVacancyById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsInteractor.getFavoriteVacancy(id).collect { vacancy ->
                log(thisName, "getFavoriteVacancyById -> $vacancy")
                isInFavorites = vacancy
                _uiState.value = DetailsScreenState.PlayHeartAnimation(vacancy, viewModelScope)
            }
        }
    }

    fun sendVacancy() {
        log(thisName, "sendVacancy()")
        _uiState.value.let { state ->
            if (state is DetailsScreenState.Content) {
                Log.e("DetailsViewModel", "sendVacancy() -> ${state.vacancy.alternateUrl}")
                state.vacancy.alternateUrl
                sharingInteractor.sendVacancy(state.vacancy.alternateUrl)
            } else {
                return
            }
        }
    }

    fun writeEmail(context: Context) {
        log(thisName, "writeEmail()")
        _uiState.value.let { state ->
            if (state is DetailsScreenState.Content &&
                state.vacancy.contactEmail != context.getString(R.string.no_info)
            ) {
                sharingInteractor.writeEmail(state.vacancy.contactEmail)
            } else {
                return
            }
        }
    }

    fun makeCall() {
        log(thisName, "makeCall()")
        _uiState.value.let { state ->
            if (state is DetailsScreenState.Content &&
                state.vacancy.contactPhones.isNotEmpty()
            ) {
                sharingInteractor.makeCall(state.vacancy.contactPhones[0])
            } else {
                return
            }
        }
    }

    fun getVacancyByID(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = DetailsScreenState.Loading
            detailsInteractor.getFullVacancyInfoById(id).collect { result ->
                when (result) {
                    is NetworkResponse.Success -> {
                        log(thisName, "NetworkResponse.Success -> ${result.thisName}")
                        _uiState.value = DetailsScreenState.Content(result.data)
                        vacancy = result.data
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
                    }
                }
            }
        }
    }
}