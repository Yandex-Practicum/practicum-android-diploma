package ru.practicum.android.diploma.features.vacancydetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.features.vacancydetails.domain.SharingInteractor
import ru.practicum.android.diploma.features.vacancydetails.presentation.models.VacancyDetailsEvent
import ru.practicum.android.diploma.features.vacancydetails.presentation.models.VacancyDetailsState

class VacancyDetailsViewModel(private val sharingInteractor: SharingInteractor) : ViewModel() {

    private val _screenState = MutableLiveData<VacancyDetailsState>()
    val screenState: LiveData<VacancyDetailsState> get() = _screenState

    private val _externalNavEvent = MutableLiveData<Event<VacancyDetailsEvent>>()
    val externalNavEvent: LiveData<Event<VacancyDetailsEvent>> get() = _externalNavEvent


    fun getVacancyById(id: String?) {}

    fun composeEmail(address: String, vacancyName: String) {
        val email = sharingInteractor.createEmailObject(address, vacancyName)
        email?.let {
            _externalNavEvent.postValue(
                Event(VacancyDetailsEvent.ComposeEmail(email))
            )
        }
    }

    fun generateShareText(strings: List<String>) {
        val message = sharingInteractor.generateShareText(strings)
        _externalNavEvent.postValue(
            Event(VacancyDetailsEvent.ShareVacancy(message))
        )
    }

}