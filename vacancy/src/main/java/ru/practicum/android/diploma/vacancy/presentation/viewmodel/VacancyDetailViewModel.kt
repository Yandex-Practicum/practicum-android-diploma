package ru.practicum.android.diploma.vacancy.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.vacancy.domain.model.Vacancy
import ru.practicum.android.diploma.vacancy.domain.usecase.VacancyDetailInteractor
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.state.VacancyDetailState

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
        viewModelScope.launch {
            vacancyFlow.collect { vacancy ->
                _vacancyStateLiveData.postValue(
                    vacancy.first?.let { VacancyDetailState.Content(it) }
                        ?: vacancy.second?.let { VacancyDetailState.Error(it) }
                        ?: VacancyDetailState.Empty
                )
            }
        }
    }
}
