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
import ru.practicum.android.diploma.presentation.models.vacancies.VacanciesState
import ru.practicum.android.diploma.util.Resource

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
                            when (val data = resource.data) {
                                null -> _state.value = VacanciesState.Empty
                                else -> {
                                    val (vacancies, totalFound) = data
                                    _state.value = if (vacancies.isEmpty()) {
                                        VacanciesState.Empty
                                    } else {
                                        VacanciesState.Success(vacancies, totalFound)
                                    }
                                }
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

    fun resetState() {
        _state.value = VacanciesState.Initial
    }

}
