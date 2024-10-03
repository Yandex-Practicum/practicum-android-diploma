package ru.practicum.android.diploma.vacancy.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.vacancy.domain.usecase.VacancyDetailInteractor
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.state.VacancyDetailState

class VacancyDetailViewModel(
    private val vacancyId: String,
    private val vacancyInteractor: VacancyDetailInteractor,
) : ViewModel() {
    private val _vacancyStateLiveData = MutableLiveData<VacancyDetailState>()
    fun observeVacancyState(): LiveData<VacancyDetailState> = _vacancyStateLiveData
    fun showVacancy() {
        _vacancyStateLiveData.postValue(VacancyDetailState.Loading)
        viewModelScope.launch {
            vacancyInteractor.getVacancyNetwork(vacancyId).collect { vacancy ->
                if (vacancy.first != null) {
                    _vacancyStateLiveData.postValue(vacancy.first?.let { VacancyDetailState.Content(it) })
                } else {
                    _vacancyStateLiveData.postValue(vacancy.second?.let { VacancyDetailState.Error(it) })
                }
            }
        }
    }
}
