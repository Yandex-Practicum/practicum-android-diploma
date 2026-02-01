package ru.practicum.android.diploma.ui.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetailsError

class VacancyViewModel(
    private val vacancyId: String,
    private val detailsInteractor: VacancyDetailsInteractor
) : ViewModel() {

    private val _state = MutableLiveData<VacancyState>()
    val state: LiveData<VacancyState> = _state
    private var requestJob: Job? = null

    fun load() {
        _state.value = VacancyState.Loading
        requestJob?.cancel()

        requestJob = viewModelScope.launch {
            val result = detailsInteractor.getVacancyDetails(vacancyId)

            _state.value = when {
                result.data != null -> {
                    val currentVacancy = result.data

                    VacancyState.Content(
                        vacancy = currentVacancy,
                        salaryFormatted = currentVacancy.salaryTitle,
                        employerAddress = getEmployerAddress(currentVacancy),
                        skillsText = currentVacancy?.skills?.filter { it.isNotBlank() },
                        hasContacts = hasContacts(currentVacancy)
                    )
                }

                else -> {
                    VacancyState.Error(result.error ?: VacancyDetailsError.Server)
                }
            }
        }
    }

    private fun hasContacts(vacancy: Vacancy): Boolean {
        return !vacancy.contactName.isNullOrEmpty() ||
            !vacancy.email.isNullOrEmpty() ||
            vacancy.phones?.isNotEmpty() == true
    }

    private fun getEmployerAddress(vacancy: Vacancy): String {
        return vacancy.fullAddress?.takeIf { it.isNotBlank() } ?: vacancy.areaName
    }
}
