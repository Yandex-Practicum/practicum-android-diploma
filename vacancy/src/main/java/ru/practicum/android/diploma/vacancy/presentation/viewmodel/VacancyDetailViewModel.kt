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
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.state.VacancyFavoriteState

class VacancyDetailViewModel(
    private val vacancyInteractor: VacancyDetailInteractor,
) : ViewModel() {

    private val _vacancyStateLiveData = MutableLiveData<VacancyDetailState>()
    fun observeVacancyState(): LiveData<VacancyDetailState> = _vacancyStateLiveData

    fun showVacancyNetwork(vacancyId: String) {
        showVacancy(vacancyInteractor.getVacancyNetwork(vacancyId))
    }

    fun showVacancyDb(vacancyId: Int) {
        showVacancy(vacancyInteractor.getVacancyDb(vacancyId))
    }

    private fun showVacancy(vacancyFlow: Flow<Pair<Vacancy?, String?>>) {
        _vacancyStateLiveData.postValue(VacancyDetailState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            vacancyFlow.collect { vacancy ->
                _vacancyStateLiveData.postValue(
                    vacancy.first?.let { VacancyDetailState.Content(it) }
                        ?: vacancy.second?.let { VacancyDetailState.Error(it) }
                        ?: VacancyDetailState.Empty
                )
            }
        }
    }

    private val _vacancyFavoriteStateLiveData = MutableLiveData<VacancyFavoriteState>()
    fun observeVacancyFavoriteState(): LiveData<VacancyFavoriteState> = _vacancyFavoriteStateLiveData

    private val _vacancyFavoriteMessageStateLiveData = MutableLiveData<VacancyFavoriteMessageState>()
    fun observeVacancyFavoriteMessageState(): LiveData<VacancyFavoriteMessageState> = _vacancyFavoriteMessageStateLiveData

    fun startInitVacancyFavoriteMessageState() {
        _vacancyFavoriteMessageStateLiveData.postValue(VacancyFavoriteMessageState.Empty)
    }

    fun checkVacancyFavorite(vacancy: Vacancy) {
        viewModelScope.launch(Dispatchers.IO) {
            vacancyInteractor.checkVacancyExists(vacancy.idVacancy).collect { (existingId, message) ->
                when {
                    existingId != null && existingId > 0 -> {
                        vacancyInteractor.deleteVacancy(vacancy.idVacancy).collect { (numberOfDeleted, deleteMessage) ->
                            if (numberOfDeleted != null && numberOfDeleted > 0) {
                                _vacancyFavoriteStateLiveData.postValue(VacancyFavoriteState.NotFavorite)
                            } else {
                                updateVacancyFavoriteMessageState(VacancyFavoriteMessageState.NoDeleteFavorite)
                            }
                        }
                    }
                    existingId != null && existingId <= 0 -> {
                        vacancyInteractor.addVacancy(vacancy).collect { (addId, deleteMessage) ->
                            if (addId != null && addId != -1L) {
                                _vacancyFavoriteStateLiveData.postValue(VacancyFavoriteState.Favorite)
                            } else {
                                updateVacancyFavoriteMessageState(VacancyFavoriteMessageState.NoAddFavorite)
                            }
                        }
                    }
                    existingId == null && message != null -> {
                        updateVacancyFavoriteMessageState(VacancyFavoriteMessageState.Error)
                    }
                }
            }
        }
    }

    private suspend fun updateVacancyFavoriteMessageState(state: VacancyFavoriteMessageState) {
        _vacancyFavoriteMessageStateLiveData.postValue(state)
        delay(100)
        _vacancyFavoriteMessageStateLiveData.postValue(VacancyFavoriteMessageState.Empty)
    }
}
