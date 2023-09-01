package ru.practicum.android.diploma.features.vacancydetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.features.vacancydetails.domain.SharingInteractor
import ru.practicum.android.diploma.features.vacancydetails.domain.VacancyDetailsInteractor
import ru.practicum.android.diploma.features.vacancydetails.presentation.models.VacancyDetailsEvent
import ru.practicum.android.diploma.features.vacancydetails.presentation.models.VacancyDetailsState
import ru.practicum.android.diploma.features.vacancydetails.presentation.models.VacancyDetailsUiMapper
import ru.practicum.android.diploma.root.data.Outcome

class VacancyDetailsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val vacancyDetailsInteractor: VacancyDetailsInteractor,
    private val vacancyDetailsUiMapper: VacancyDetailsUiMapper
) : ViewModel() {

    private val _screenState = MutableLiveData<VacancyDetailsState>()
    val screenState: LiveData<VacancyDetailsState> get() = _screenState

    private val _externalNavEvent = MutableLiveData<Event<VacancyDetailsEvent>>()
    val externalNavEvent: LiveData<Event<VacancyDetailsEvent>> get() = _externalNavEvent


    fun getVacancyById(id: String) {
        if (id.isNotEmpty()) {
            viewModelScope.launch {

                _screenState.postValue(VacancyDetailsState.Loading)

                val result = vacancyDetailsInteractor.getVacancyById(id)
                when (result) {
                    is Outcome.Success -> {
                        result.data?.let {
                            _screenState.postValue(
                                VacancyDetailsState.Content(
                                    vacancyDetailsUiMapper(it)
                                )
                            )
                        }
                    }

                    else -> {
                        _screenState.postValue(VacancyDetailsState.Error)
                    }
                }
            }
        }
    }

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