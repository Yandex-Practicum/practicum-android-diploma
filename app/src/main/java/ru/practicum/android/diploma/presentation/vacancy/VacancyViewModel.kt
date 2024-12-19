package ru.practicum.android.diploma.presentation.vacancy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto
import ru.practicum.android.diploma.domain.vacancy.VacancyInteractor
import ru.practicum.android.diploma.domain.vacancy.model.VacancyState

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
            "Is empty" -> {
                renderState(VacancyState.Empty)
            }

            "Not connect internet" -> {
                renderState(VacancyState.Error)
            }

            "Ошибка сервера" -> {
                renderState(VacancyState.Error)
            }

            "404" -> {
                renderState(VacancyState.Error)
            }
        }
    }

    private fun renderState(state: VacancyState) {
        vacancyScreenStateLiveData.postValue(state)
    }
}
