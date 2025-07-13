package ru.practicum.android.diploma.presentation.vacancysearchscreen.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.vacancysearchscreen.impl.ErrorType
import ru.practicum.android.diploma.domain.models.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy
import ru.practicum.android.diploma.presentation.models.vacancies.VacanciesState
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.SingleEventLiveData

class VacanciesSearchViewModel(private val interactor: VacanciesInteractor) : ViewModel() {

    private val _state = MutableLiveData<VacanciesState>()
    val state: LiveData<VacanciesState> = _state

    fun searchVacancies(query: String) {
        _state.value = VacanciesState.Loading

        viewModelScope.launch {
            interactor.search(query)
                .flowOn(Dispatchers.IO)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            val vacancies = resource.data ?: emptyList()
                            _state.value = if (vacancies.isEmpty()) {
                                VacanciesState.Empty
                            } else {
                                VacanciesState.Success(vacancies)
                            }
                        }
                        is Resource.Error -> {
                            _state.value = when (resource.errorType) {
                                ErrorType.NO_INTERNET -> VacanciesState.NoInternet
                                ErrorType.SERVER_ERROR -> VacanciesState.ServerError
                                else -> VacanciesState.Empty
                            }
                        }
                    }
                }
        }
    }
}
