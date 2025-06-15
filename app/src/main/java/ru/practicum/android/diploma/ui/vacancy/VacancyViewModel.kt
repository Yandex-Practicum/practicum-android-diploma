package ru.practicum.android.diploma.ui.vacancy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.domain.vacancy.api.VacancyDetailsRepository

class VacancyViewModel(
    private val repository: VacancyDetailsRepository,
    private val mapper: VacancyDetailsMapper
) : ViewModel() {

    private val _vacancyState = MutableStateFlow<VacancyContentStateVO>(VacancyContentStateVO.Base)
    val vacancyState: StateFlow<VacancyContentStateVO> = _vacancyState.asStateFlow()

    fun loadVacancyDetails(id: String) {
        _vacancyState.value = VacancyContentStateVO.Loading

        viewModelScope.launch {
            when (val result = repository.getVacancyDetails(id)) {
                is ApiResponse.Success -> {
                    val vo = result.data.let {
                        mapper.run { it.toVO() }
                    }
                    _vacancyState.value = VacancyContentStateVO.Success(vo)
                }

                is ApiResponse.Error -> {
                    _vacancyState.value = VacancyContentStateVO.Error
                }
            }
        }
    }
}
