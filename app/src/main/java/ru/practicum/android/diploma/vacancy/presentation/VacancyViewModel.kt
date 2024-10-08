package ru.practicum.android.diploma.vacancy.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.domain.api.GetVacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

class VacancyViewModel(
    private val vacancyId: String,
    private val interactor: GetVacancyDetailsInteractor
) : ViewModel() {

    private val vacancyState = MutableLiveData<VacancyScreenState>()

    fun getVacancyState(): LiveData<VacancyScreenState> = vacancyState

    private fun loadVacancyDetails(vacancyId: String) {
        renderState(VacancyScreenState.LoadingState)
        viewModelScope.launch(Dispatchers.IO) {
            interactor.getVacancyDetails(vacancyId)
                .collect { result ->
                    when (result) {
                        null,
                        is Resource.Error -> renderState(VacancyScreenState.NetworkErrorState)
                        is Resource.Success -> processSuccessResult(result)
                    }
                }
        }

    }

    private fun renderState(state: VacancyScreenState) {
        vacancyState.postValue(state)
    }

    private fun processSuccessResult(result: Resource.Success<Vacancy>) {
        if (result.data == null) {
            renderState(VacancyScreenState.EmptyState)
        } else {
            renderState(VacancyScreenState.ContentState(result.data))
        }
    }

}
