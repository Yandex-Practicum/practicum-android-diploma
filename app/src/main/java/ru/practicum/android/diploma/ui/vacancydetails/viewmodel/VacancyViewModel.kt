package ru.practicum.android.diploma.ui.vacancydetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.favorites.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.ui.vacancydetails.state.VacancyDetailsScreenState
import ru.practicum.android.diploma.util.ResponseCode

class VacancyViewModel(
    private val currentVacancyId: Long,
    private val vacancyDetailsInteractor: VacancyDetailsInteractor,
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {

    private var vacancy: Vacancy? = null

    //    // Временное решение для кнопок "Поделиться" и "Избранное"
//    private val _vacancy = MutableLiveData<Vacancy>()
//    val vacancy: LiveData<Vacancy> get() = _vacancy

    private val _vacancyDetailsScreenState = MutableLiveData<VacancyDetailsScreenState>()
    val vacancyDetailsScreenState: LiveData<VacancyDetailsScreenState> = _vacancyDetailsScreenState

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> get() = _isFavorite


    // Временно решение для метода getFavorites, чтобы пройти проверку
    // потом удалить!
    init {
        checkFavoriteStatus()
        // здесь будем загружать детали вакансии
        searchVacancyDetails()
    }


    // Обработка нажатия на кнопку Избранное
    fun onFavoriteClicked() {
        _isFavorite.value = _isFavorite.value?.not()

        if (_isFavorite.value == true) {
            viewModelScope.launch {
                vacancy?.let { favoritesInteractor.saveVacancy(it) }
            }
        } else {
            viewModelScope.launch {
                favoritesInteractor.removeVacancyById(currentVacancyId)
            }
        }
    }

    // Метод для проверки, добавлена ли вакансия в избранное,
    // вызвать в методе getVacancy после успешного получения вакансии
    private fun checkFavoriteStatus() {
        viewModelScope.launch {
//            _vacancy.value?.let {
//                val result = favoritesInteractor.getFavoritesById(it.vacancyId)
//                _isFavorite.postValue(result)
//            }
            favoritesInteractor.getFavoritesById(currentVacancyId).collect { favouriteStatus ->
                _isFavorite.postValue(favouriteStatus)
            }
        }
    }

    private fun searchVacancyDetails() {
        _vacancyDetailsScreenState.postValue(VacancyDetailsScreenState.Loading)

        viewModelScope.launch {
            vacancyDetailsInteractor.getVacancyDetails(currentVacancyId.toString()).collect { resource ->
                handleSearchResult(resource)
            }
        }
    }

    private fun handleSearchResult(resource: Resource<Vacancy>) {
        when (resource) {
            is Resource.Error -> {
                if (resource.errorCode == ResponseCode.SERVER_ERROR.code) {
                    _vacancyDetailsScreenState.postValue(VacancyDetailsScreenState.ServerError)
                } else {
                    // Проверить, что это точно работает
                    _vacancyDetailsScreenState.postValue(VacancyDetailsScreenState.NotFoundError)
                }
            }

            is Resource.Success -> {
                if (resource.value != null) {
                    vacancy = resource.value
                    _vacancyDetailsScreenState.postValue(VacancyDetailsScreenState.Content(resource.value))
                }
            }
        }
    }
}
