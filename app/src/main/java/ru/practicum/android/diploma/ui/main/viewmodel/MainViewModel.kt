package ru.practicum.android.diploma.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchRequest
import ru.practicum.android.diploma.domain.api.Resource
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.main.Vacancy
import ru.practicum.android.diploma.ui.main.MainViewState
import ru.practicum.android.diploma.ui.main.SearchState

class MainViewModel(
    val repository: SearchRepository
) : ViewModel() {
    private val state = MutableStateFlow(MainViewState())
    fun observeState() = state.asStateFlow()

    fun onSearch(text: String) {
        if (text.isEmpty()) {
            state.update { it.copy(state = null) }
            return
        }
        state.update { it.copy(state = SearchState.Loading) }

        viewModelScope.launch {
            delay(2000) //TODO SearchVacancyInteractor(text)
            searchRequest(text)
//            state.update { it.copy(state = SearchState.Error) }
        }
    }

    var vacancyList: List<Vacancy>? = ArrayList()

    fun searchRequest(text: String){
        if (text.isNotEmpty()) {
            state.update { it.copy(state = SearchState.Loading) }

            val hm = HashMap<String,String>()
            hm.put(text,text)

            viewModelScope.launch {
                repository.makeRequest(VacanciesSearchRequest(text))
                    .collect{ vacancies ->
                        when(vacancies){
                            is Resource.Error -> {
                                state.update { it.copy(state = SearchState.Error) }
                                Log.d("SearchState", "Error")
                            }
                            is Resource.Success -> {
                                vacancyList = vacancies.data
                                if (vacancyList?.isEmpty() == true) {
                                    state.update { it.copy(state = SearchState.Empty) }
                                    Log.d("SearchState", "Empty")
                                } else {
                                    state.update { it.copy(state = SearchState.Content(vacancyList)) }
                                    Log.d("SearchState", "Data")
                                }
                            }
                        }
                    }
            }
        }
    }


    private fun processResult(foundVacancy: List<Vacancy>?) {
        val vacancy = mutableListOf<Vacancy>()

        if (foundVacancy != null) {
            vacancy.addAll(foundVacancy)
        }

        when {
            vacancy.isEmpty() -> {
                state.update { it.copy(state = SearchState.Empty) }
            }
            else -> {
                state.update { it.copy(state = SearchState.Content(vacancyList)) }
            }
        }
    }
}
