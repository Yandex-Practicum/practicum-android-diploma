package ru.practicum.android.diploma.ui.vacancy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto
import ru.practicum.android.diploma.domain.vacancy.VacancyInteractor
import ru.practicum.android.diploma.ui.search.state.VacancyError
import ru.practicum.android.diploma.util.Resource

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
            interactor.getVacancyId(id).collect { resource ->
                processResult(resource)
            }
        }
    }

    private fun processResult(resource: Resource<VacancyFullItemDto>) {
        when (resource) {
            is Resource.Success -> {
                listVacancy = resource.data
                renderState(VacancyState.Content(listVacancy!!))
            }
            is Resource.Error -> {
                when (resource.message) {
                    VacancyError.NETWORK_ERROR -> {
                        renderState(VacancyState.NetworkError)
                    }
                    VacancyError.BAD_REQUEST -> {
                        renderState(VacancyState.BadRequest)
                    }
                    VacancyError.NOT_FOUND -> {
                        renderState(VacancyState.Empty)
                    }
                    VacancyError.UNKNOWN_ERROR, VacancyError.SERVER_ERROR -> {
                        renderState(VacancyState.ServerError)
                    }
                }
            }
        }
    }

    private fun renderState(state: VacancyState) {
        vacancyScreenStateLiveData.postValue(state)
    }
}
