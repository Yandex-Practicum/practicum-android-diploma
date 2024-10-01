package ru.practicum.android.diploma.vacancy.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.vacancy.domain.usecase.VacancyDetailInteractor

class VacancyDetailViewModel(
    private val vacancyId: String,
    private val vacancyInteractor: VacancyDetailInteractor,
    private val application: Application,
) : AndroidViewModel(application) {
    private val _vacancyStateLiveData = MutableLiveData<VacancyDetailState>()
    fun observeVacancyState(): LiveData<VacancyDetailState> = _vacancyStateLiveData
    fun showVacancy() = viewModelScope.launch {
        _vacancyStateLiveData.postValue(VacancyDetailState.Loading)
        vacancyInteractor.listVacancy(vacancyId).collect { vacancy ->
            if (vacancy.first != null) {
                _vacancyStateLiveData.postValue(VacancyDetailState.Content(vacancy.first!!))
            } else {
                _vacancyStateLiveData.postValue(VacancyDetailState.Error(vacancy.second!!))
            }
        }
    }
}
