package ru.practicum.android.diploma.ui.vacancy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.domain.vacancy.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetails

class VacancyViewModel(
    private val repository: VacancyDetailsRepository
) : ViewModel() {
    private val _vacancyDetails = MutableStateFlow<VacancyDetails?>(null)
    val vacancyDetails: StateFlow<VacancyDetails?> = _vacancyDetails

    fun loadVacancyDetails(id: String) {
        viewModelScope.launch {
            when (val result = repository.getVacancyDetails(id)) {
                is ApiResponse.Success -> _vacancyDetails.value = result.data
                is ApiResponse.Error -> {
                    // обработка ошибки
                }
            }
        }
    }
}
