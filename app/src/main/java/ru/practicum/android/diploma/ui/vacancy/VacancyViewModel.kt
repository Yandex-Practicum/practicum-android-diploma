package ru.practicum.android.diploma.ui.vacancy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.domain.db.FavoriteInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.vacancy.api.VacancyDetailsRepository

class VacancyViewModel(
    private val repository: VacancyDetailsRepository,
    private val mapper: VacancyDetailsMapper,
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {
    private var vacancy: VacancyDetails? = null

    private val _vacancyState = MutableStateFlow<VacancyContentStateVO>(VacancyContentStateVO.Base)
    val vacancyState: StateFlow<VacancyContentStateVO> = _vacancyState.asStateFlow()

    fun loadVacancyDetails(id: String) {
        _vacancyState.value = VacancyContentStateVO.Loading

        viewModelScope.launch {
            when (val result = repository.getVacancyDetails(id)) {
                is ApiResponse.Success -> {
                    vacancy = result.data
                    val vo = result.data?.let {
                        mapper.run { it.toVO() }
                    }
                    if (vo != null) {
                        _vacancyState.value = VacancyContentStateVO.Success(vo)
                    } else {
                        _vacancyState.value = VacancyContentStateVO.Error
                    }
                }

                is ApiResponse.Error -> {
                    _vacancyState.value = VacancyContentStateVO.Error
                }
            }
        }
    }

    fun changeFavorite() {
        vacancy?.let {
            viewModelScope.launch {
                if (it.isFavorite) {
                    favoriteInteractor.delFromFavorite(it)
                } else {
                    favoriteInteractor.addToFavorite(it)
                }
                _vacancyState.value = VacancyContentStateVO.Refresh
            }
        }
    }
}
