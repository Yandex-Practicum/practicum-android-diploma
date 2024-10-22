package ru.practicum.android.diploma.vacancy.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.vacancy.domain.model.Vacancy
import ru.practicum.android.diploma.vacancy.domain.usecase.VacancyDetailInteractor
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.state.VacancyDetailState
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.state.VacancyFavoriteMessageState

private const val COMMAND_TO_REMOVE_VACANCY_FROM_FAVORITES = "404"
private const val DELAY_TO_DEFAULT_STATE_MESSAGE = 100L

internal class VacancyDetailViewModel(
    private val vacancyInteractor: VacancyDetailInteractor,
) : ViewModel() {

    private val _vacancyStateLiveData = MutableLiveData<VacancyDetailState>()
    fun observeVacancyState(): LiveData<VacancyDetailState> = _vacancyStateLiveData

    fun showVacancyNetwork(vacancyId: String) {
        showVacancy(vacancyId.toInt(), vacancyInteractor.getVacancyNetwork(vacancyId))
    }

    fun showVacancyDb(vacancyId: Int) {
        showVacancy(vacancyId, vacancyInteractor.getVacancyDb(vacancyId))
    }

    private fun showVacancy(vacancyId: Int, vacancyFlow: Flow<Pair<Vacancy?, String?>>) {
        _vacancyStateLiveData.postValue(VacancyDetailState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            vacancyFlow.collect { vacancy ->
                _vacancyStateLiveData.postValue(
                    vacancy.first?.let { VacancyDetailState.Content(it) }
                        ?: vacancy.second?.let {
                            cleanVacancyFromDbOnError404(it, vacancyId)
                            VacancyDetailState.Error(it)
                        }
                        ?: VacancyDetailState.Empty
                )
            }
        }
    }

    private suspend fun cleanVacancyFromDbOnError404(messageError: String, vacancyId: Int) {
        if (messageError == COMMAND_TO_REMOVE_VACANCY_FROM_FAVORITES) {
            vacancyInteractor.checkVacancyExists(vacancyId).collect { (existingId, _) ->
                existingId?.let { id ->
                    if (id > 0) {
                        deleteFavoriteVacancy(vacancyId)
                    }
                }
            }
        }
    }

    private val _vacancyFavoriteMessageStateLiveData = MutableLiveData<VacancyFavoriteMessageState>()
    fun observeVacancyFavoriteMessageState(): LiveData<VacancyFavoriteMessageState> =
        _vacancyFavoriteMessageStateLiveData

    fun modifyingStatusOfVacancyFavorite(vacancy: Vacancy) {
        viewModelScope.launch(Dispatchers.IO) {
            vacancyInteractor.checkVacancyExists(vacancy.idVacancy).collect { (existingId, _) ->
                existingId?.let { id ->
                    if (id > 0) {
                        deleteFavoriteVacancy(vacancy.idVacancy)
                    } else {
                        addFavoriteVacancy(vacancy)
                    }
                } ?: updateVacancyFavoriteMessageState(VacancyFavoriteMessageState.Error)
            }
        }
    }

    private suspend fun deleteFavoriteVacancy(vacancyId: Int) {
        vacancyInteractor.deleteVacancy(vacancyId).collect { (numberOfDeleted, _) ->
            if (numberOfDeleted != null && numberOfDeleted > 0) {
                favoriteStateLiveData.postValue(false)
            } else {
                updateVacancyFavoriteMessageState(VacancyFavoriteMessageState.NoDeleteFavorite)
            }
        }
    }

    private suspend fun addFavoriteVacancy(addVacancy: Vacancy) {
        vacancyInteractor.addVacancy(addVacancy).collect { (addId, _) ->
            if (addId != null && addId != -1L) {
                favoriteStateLiveData.postValue(true)
            } else {
                updateVacancyFavoriteMessageState(VacancyFavoriteMessageState.NoAddFavorite)
            }
        }
    }

    private suspend fun updateVacancyFavoriteMessageState(state: VacancyFavoriteMessageState) {
        _vacancyFavoriteMessageStateLiveData.postValue(state)
        delay(DELAY_TO_DEFAULT_STATE_MESSAGE)
        _vacancyFavoriteMessageStateLiveData.postValue(VacancyFavoriteMessageState.Empty)
    }

    private val favoriteStateLiveData = MutableLiveData<Boolean>()
    fun observeFavoriteState(): LiveData<Boolean> = favoriteStateLiveData
    fun updateFavorite(id: Int) {
        viewModelScope.launch {
            vacancyInteractor.checkVacancyExists(id).collect { (existingId, _) ->
                existingId?.let { id ->
                    if (id > 0) {
                        favoriteStateLiveData.postValue(true)
                    } else {
                        favoriteStateLiveData.postValue(false)
                    }
                }
            }
        }
    }

    fun share(shareLink: String) {
        vacancyInteractor.share(shareLink)
    }
}
