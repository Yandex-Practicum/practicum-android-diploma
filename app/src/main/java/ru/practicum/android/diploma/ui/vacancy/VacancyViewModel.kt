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
    private var vacancyLocal: VacancyDetails? = null

    private val _vacancyState = MutableStateFlow<VacancyContentStateVO>(VacancyContentStateVO.Base)
    val vacancyState: StateFlow<VacancyContentStateVO> = _vacancyState.asStateFlow()

    fun loadVacancyDetails(id: String) {
        _vacancyState.value = VacancyContentStateVO.Loading

        viewModelScope.launch {
            var vacancyDetails: VacancyDetails? = null
            favoriteInteractor.getFavoriteById(id).collect { vacancy ->
                vacancyDetails = vacancy
            }
            vacancyDetails = vacancyLocal ?: vacancyDetails
            if (vacancyDetails != null) {
                loadingFromFavorite(id)
            } else {
                loadingFromInternet(id)
            }
        }
    }

    fun changeFavorite() {
        vacancyLocal?.let { vacancy ->
            viewModelScope.launch {
                val isFavorite = vacancy.isFavorite
                if (isFavorite) {
                    favoriteInteractor.delFromFavorite(vacancy)
                } else {
                    favoriteInteractor.addToFavorite(vacancy)
                }
                vacancyLocal = vacancy.copy(isFavorite = !isFavorite)
                _vacancyState.value = VacancyContentStateVO.SetFavorite(!vacancy.isFavorite)
            }
        }
    }

    private suspend fun loadingFromFavorite(id: String) {
        favoriteInteractor.getFavoriteById(id).collect { vacancy ->
            processResult(vacancy)
        }
    }

    private suspend fun loadingFromInternet(id: String) {
        when (val result = repository.getVacancyDetails(id)) {
            is ApiResponse.Success -> {
                processResult(result.data)
            }

            is ApiResponse.Error -> {
                _vacancyState.value = VacancyContentStateVO.Error
            }
        }
    }

    private fun processResult(vacancy: VacancyDetails?) {
        vacancyLocal = vacancy
        if (vacancy != null) {
            val vo = mapper.run { vacancy.toVO() }
            _vacancyState.value = VacancyContentStateVO.Success(vo)
        } else {
            _vacancyState.value = VacancyContentStateVO.Error
        }
    }
}
