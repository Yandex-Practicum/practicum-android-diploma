package ru.practicum.android.diploma.vacancy.presentation

import android.text.BoringLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.favourites.domain.api.AddToFavouritesInteractor
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.domain.usecase.DetailVacancyUseCase
import ru.practicum.android.diploma.vacancy.domain.usecase.MakeCallUseCase
import ru.practicum.android.diploma.vacancy.domain.usecase.SendEmailUseCase
import ru.practicum.android.diploma.vacancy.domain.usecase.ShareVacancyUseCase

class VacancyViewModel(
    private val detailVacancyUseCase: DetailVacancyUseCase,
    private val makeCallUseCase: MakeCallUseCase,
    private val sendEmailUseCase: SendEmailUseCase,
    private val shareVacancyUseCase: ShareVacancyUseCase,
    private val addToFavouritesInteractor: AddToFavouritesInteractor
) : ViewModel() {
    private val stateLiveData = MutableLiveData<VacancyScreenState>()

    private var isInFavourites: Boolean = false

    private var vacancy: DetailVacancy? = null

    init {
        getDetailVacancyById()
        setFavouritesStatus()
    }

    fun observeState(): LiveData<VacancyScreenState> = stateLiveData

    private fun getDetailVacancyById() {
        stateLiveData.postValue(VacancyScreenState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            detailVacancyUseCase.execute(TEST_ID).collect {
                when (it) {
                    is Resource.Success -> {
                        renderState(VacancyScreenState.Content(it.data!!))
                        vacancy = it.data!!
                    }
                    is Resource.InternetError -> renderState(VacancyScreenState.Error)
                    is Resource.ServerError -> renderState(VacancyScreenState.Error)
                }
            }
        }
    }

    fun makeCall(phoneNumber: String) {
        makeCallUseCase.execute(phoneNumber)
    }

    fun sendEmail(email: String) {
        sendEmailUseCase.execute(email)
    }

    fun shareVacancy(url: String) {
        shareVacancyUseCase.execute(url)
    }

    private fun renderState(vacancyScreenState: VacancyScreenState) {
        stateLiveData.postValue(vacancyScreenState)
    }

    companion object {
        private const val TEST_ID = 93_485_145L
    }

    fun getFavouritesStatus(): Boolean {
        return isInFavourites
    }

    fun setFavouritesStatus() {
        viewModelScope.launch {
            isInFavourites = addToFavouritesInteractor.checkVacancyInFavourites(vacancy!!.id)
        }
    }

    fun setFavourites() {
        if (isInFavourites) {
            viewModelScope.launch {
                addToFavouritesInteractor.removeFromFavourites(vacancy!!)
            }
        } else {
            viewModelScope.launch {
                addToFavouritesInteractor.addToFavourites(vacancy!!)
            }
        }
    }
}
