package ru.practicum.android.diploma.ui.vacancy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto
import ru.practicum.android.diploma.domain.vacancy.VacancyInteractor

class VacancyViewModel(
    application: Application,
    private val interactor: VacancyInteractor
) : AndroidViewModel(application) {

    private val vacancyScreenStateLiveData = MutableLiveData<VacancyState>()
    private var listVacancy: VacancyFullItemDto? = null

    val getVacancyScreenStateLiveData: LiveData<VacancyState> = vacancyScreenStateLiveData

    fun getVacancyRessurces(id: String) {
        renderState(VacancyState.Loading)
        viewModelScope.launch {
            interactor.getVacancyId(id).collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun processResult(vacancy: VacancyFullItemDto?, errorMessage: String?) {
        if (vacancy != null) {
            listVacancy = vacancy
            renderState(VacancyState.Content(listVacancy!!))
        }

        when (errorMessage) {
            "Network Error" -> {
                renderState(VacancyState.NetworkError)
            }

            "Bad Request" -> {
                renderState(VacancyState.BadRequest)
            }

            "Not Found" -> {
                renderState(VacancyState.Empty)
            }

            "Unknown Error" -> {
                renderState(VacancyState.ServerError)
            }
            "Server Error" -> {
                renderState(VacancyState.ServerError)
            }
        }
    }

    private fun renderState(state: VacancyState) {
        vacancyScreenStateLiveData.postValue(state)
    }
}
