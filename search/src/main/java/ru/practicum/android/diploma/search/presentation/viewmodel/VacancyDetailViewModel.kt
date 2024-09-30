package ru.practicum.android.diploma.search.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.usecase.VacanciesInteractor

class VacancyDetailViewModel(
    private val vacancyId: String,
    private val vacancyInteractor: VacanciesInteractor,
    private val application: Application,
) : AndroidViewModel(application) {

    private val _vacancyStateLiveData = MutableLiveData<VacancyListState>()
    fun observeVacancyState(): LiveData<VacancyListState> = _vacancyStateLiveData

    fun showVacancy() {
        _vacancyStateLiveData.postValue(VacancyListState.Loading)
        viewModelScope.launch {
            vacancyInteractor.listVacancy(vacancyId).collect { vacancy ->
                if (vacancy.first != null) _vacancyStateLiveData.postValue(VacancyListState.Content(vacancy.first!!))
                else _vacancyStateLiveData.postValue(VacancyListState.Error(vacancy.second!!))
            }
        }
    }
}
