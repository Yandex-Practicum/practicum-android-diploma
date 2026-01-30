package ru.practicum.android.diploma.ui.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.VacancyDetailsInteractor

class VacancyViewModel(
    private val vacancyId: String,
    private val detailsInteractor: VacancyDetailsInteractor
) : ViewModel() {

    private val _state = MutableLiveData<VacancyState>()
    val state: LiveData<VacancyState> = _state
    private var requestJob: Job? = null

    private fun load() {
        _state.value = VacancyState.Loading
        requestJob?.cancel()
        requestJob = viewModelScope.launch {
            val result = detailsInteractor.getVacancyDetails(vacancyId)
            if (result.code == CODE_OK && result.data != null) {
                _state.value = VacancyState.Content(result.data)
            } else if (result.code == CODE_NOT_FOUND) {
                _state.value = VacancyState.Empty
            } else {
                _state.value = VacancyState.Error
            }
        }
    }

    companion object {
        const val CODE_OK = 200
        const val CODE_NOT_FOUND = 404
    }
}
