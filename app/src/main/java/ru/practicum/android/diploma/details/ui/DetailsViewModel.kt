package ru.practicum.android.diploma.details.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.details.domain.api.AddVacancyToFavoritesUseCase
import ru.practicum.android.diploma.details.domain.api.CheckIfVacancyInFavsUseCase
import ru.practicum.android.diploma.details.domain.api.GetFullVacancyInfoByIdUseCase
import ru.practicum.android.diploma.details.domain.api.RemoveVacancyFromFavoritesUseCase
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.sharing.domain.api.SharingInteractor
import ru.practicum.android.diploma.util.functional.Failure


class DetailsViewModel @AssistedInject constructor(
    logger: Logger,
    private val addVacancyToFavoritesUseCase: AddVacancyToFavoritesUseCase,
    private val getFullVacancyInfoByIdUseCase: GetFullVacancyInfoByIdUseCase,
    private val removeVacancyFromFavoritesUseCase: RemoveVacancyFromFavoritesUseCase,
    private val checkIfVacancyInFavsUseCase: CheckIfVacancyInFavsUseCase,
    private val sharingInteractor: SharingInteractor,
    @Assisted("vacancyId")
    vacancyId: String
) : BaseViewModel(logger) {

    private val _uiState = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Default)
    val uiState: StateFlow<DetailsScreenState> = _uiState

    private var vacancy: VacancyFullInfo? = null

    init {
        getVacancyByID(vacancyId)
    }

    fun handleAddToFavsButton() {
        vacancy?.let { vacancy ->
            viewModelScope.launch {
                val message = if (checkIfVacancyInFavsUseCase(vacancy.id)) {
                    removeFromFavs(vacancy.id)
                    "vacancy removed from favs"
                } else {
                    addToFavs(vacancy)
                    "vacancy added to favs"
                }
                log(TAG, "handleAddToFavsButton $message")
            }
        }
    }

    private fun addToFavs(vacancy: VacancyFullInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            addVacancyToFavoritesUseCase(vacancy).collect {
                log(TAG, "${vacancy.id} inserted")
                _uiState.value = DetailsScreenState.AddAnimation
            }
        }
    }

    private fun removeFromFavs(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            removeVacancyFromFavoritesUseCase(id).collect {
                log(TAG, "$id deleted")
                _uiState.value = DetailsScreenState.DeleteAnimation
            }
        }
    }

    private fun getVacancyByID(id: String) {
        _uiState.value = DetailsScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getFullVacancyInfoByIdUseCase(id).fold(
                ::handleFailure,
                ::handleSuccess
            )
        }
    }

    override fun handleFailure(failure: Failure) {
        super.handleFailure(failure)
        if (failure is Failure.Offline) _uiState.value = DetailsScreenState.Offline(failure)
        else _uiState.value = DetailsScreenState.Error(failure)
    }

    private fun handleSuccess(vacancy: VacancyFullInfo) {
        log(TAG, "handleSuccess -> company    ${vacancy.company}")
        viewModelScope.launch {
            if (!vacancy.isInFavorite) {
                delay(LOADING_SCREEN_DURATION)
            }
            _uiState.value = DetailsScreenState.Content(vacancy)
        }
        this.vacancy = vacancy
    }

    fun sendVacancy() {
        log(TAG, "sendVacancy()")
        _uiState.value.let { state ->
            if (state is DetailsScreenState.Content) {
                state.vacancy.alternateUrl
                sharingInteractor.sendVacancy(state.vacancy.alternateUrl)
            } else {
                return
            }
        }
    }

    fun writeEmail(context: Context) {
        log(TAG, "writeEmail()")
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
        log(TAG, "makeCall()")
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

    @AssistedFactory
    interface Factory{
        fun create(@Assisted("vacancyId") vacancyId: String): DetailsViewModel
    }
    companion object{
        private const val TAG = "DetailsViewModel"
        private const val LOADING_SCREEN_DURATION = 400L

        @Suppress("UNCHECKED_CAST")
        fun provideDetailsViewModelFactory(factory: Factory, vacancyId: String): ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(vacancyId) as T
                }
            }
        }
    }
}