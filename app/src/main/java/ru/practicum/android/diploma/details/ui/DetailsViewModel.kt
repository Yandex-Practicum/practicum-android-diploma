package ru.practicum.android.diploma.details.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.details.domain.DetailsInteractor
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.sharing.domain.api.SharingInteractor
import ru.practicum.android.diploma.util.thisName


class DetailsViewModel @AssistedInject constructor(
    private val logger: Logger,
    private val detailsInteractor: DetailsInteractor,
    private val sharingInteractor: SharingInteractor,
    @Assisted("vacancyId")
    private val vacancyId: String
) : ViewModel() {

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
        logger.log(thisName, "handleAddToFavsButton $message")
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
    fun log(tag:String, text:String){
        logger.log(thisName, text)
    }

    fun writeEmail(context: Context) {
        log(thisName, "writeEmail()")
        _uiState.value.let { state ->
            if (state is DetailsScreenState.Content &&
                state.vacancy.contactEmail != context.getString(R.string.no_info)) {
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
                state.vacancy.contactPhones.isNotEmpty()) {
                sharingInteractor.makeCall(state.vacancy.contactPhones[0])
            } else {
                return
            }
        }
    }

    fun getVacancyByID(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = DetailsScreenState.Loading
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
                    }
                }
            }
        }
    }


    @AssistedFactory
    interface Factory{
        fun create(@Assisted("vacancyId") vacancyId: String): DetailsViewModel
    }
    companion object{

        fun provideDetailsViewModelFactory(factory: Factory, vacancyId: String): ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(vacancyId) as T
                }
            }
        }
    }
}