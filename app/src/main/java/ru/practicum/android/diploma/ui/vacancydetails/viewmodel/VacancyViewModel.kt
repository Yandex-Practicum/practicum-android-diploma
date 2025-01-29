package ru.practicum.android.diploma.ui.vacancydetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.favorites.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.sharing.api.ShareVacancyUseCase
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.ui.vacancydetails.state.VacancyDetailsScreenState
import ru.practicum.android.diploma.util.ResponseCode

class VacancyViewModel(
    private val currentVacancyId: Long,
    private val isFromFavoritesScreen: Boolean,
    private val vacancyDetailsInteractor: VacancyDetailsInteractor,
    private val favoritesInteractor: FavoritesInteractor,
    private val shareVacancyUseCase: ShareVacancyUseCase
) : ViewModel() {

    private var vacancy: Vacancy? = null

    private val _vacancyDetailsScreenState = MutableLiveData<VacancyDetailsScreenState>()
    val vacancyDetailsScreenState: LiveData<VacancyDetailsScreenState> = _vacancyDetailsScreenState

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    init {
        checkFavoriteStatus()
        chooseVacancyDownloadStrategy()
    }

    private fun chooseVacancyDownloadStrategy() {
        if (isFromFavoritesScreen) {
            getVacancyDetailsFromBD()
            // Метод получения вакансии из базы вставить сюда.
        } else {
            searchVacancyDetails()
        }
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
            _isFavorite.postValue(favoritesInteractor.checkFavoriteStatus(currentVacancyId))
        }
    }

    private fun searchVacancyDetails() {
        _vacancyDetailsScreenState.postValue(VacancyDetailsScreenState.Loading)

        viewModelScope.launch {
            vacancyDetailsInteractor.getVacancyDetails(currentVacancyId.toString())
                .collect { resource ->
                    handleResult(resource)
                }
        }
    }

    private fun getVacancyDetailsFromBD() {
        _vacancyDetailsScreenState.postValue(VacancyDetailsScreenState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val resource: Resource<Vacancy> = if (favoritesInteractor.checkFavoriteStatus(currentVacancyId)) {
                vacancy = favoritesInteractor.getVacancyByID(currentVacancyId)
                Resource.Success(vacancy!!)
            } else {
                Resource.Error(ResponseCode.DATABASE_ERROR.code)
            }
            handleResult(resource)
        }
    }

    private fun handleResult(resource: Resource<Vacancy>) {
        when (resource) {
            is Resource.Error -> {
                _vacancyDetailsScreenState.postValue(VacancyDetailsScreenState.ServerError)
            }
            is Resource.Success -> {
                if (resource.value != null) {
                    vacancy = resource.value
                    _vacancyDetailsScreenState.postValue(VacancyDetailsScreenState.Content(resource.value))
                } else {
                    // Проверить, что это точно работает
                    // Удаляем из бд, если вакансия пришла как null
                    viewModelScope.launch {
                        favoritesInteractor.removeVacancyById(currentVacancyId)
                    }
                    _vacancyDetailsScreenState.postValue(VacancyDetailsScreenState.NotFoundError)
                }
            }
        }
    }

    fun shareVacancy() {
        val linkToShare = vacancy?.alternateUrl

        if (linkToShare != null) {
            viewModelScope.launch {
                shareVacancyUseCase.execute(linkToShare)
            }
        }
    }
}
