package ru.practicum.android.diploma.presentation.vacancysearchscreen.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy
import ru.practicum.android.diploma.util.Resource

class VacanciesSearchViewModel(private val interactor: VacanciesInteractor) : ViewModel() {

    private val _vacancies = MutableLiveData<List<Vacancy>>()
    val vacancies: LiveData<List<Vacancy>> = _vacancies

    fun searchVacancies(query: String) {
        viewModelScope.launch {
            interactor.search(query)
                .flowOn(Dispatchers.IO)
                .collect { data ->
                    when (data) {
                        is Resource.Success -> {
                            val results = data.data ?: emptyList()
                            _vacancies.value = results
                        }

                        is Resource.Error -> {
                            _vacancies.value = emptyList()
                        }
                    }
                }
        }
    }
}
