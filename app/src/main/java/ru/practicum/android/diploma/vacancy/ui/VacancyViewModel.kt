package ru.practicum.android.diploma.vacancy.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.vacancy.domain.api.VacancyInteractor

class VacancyViewModel(
    private val vacancyInteractor: VacancyInteractor,
) : ViewModel() {

    private val _screenState = MutableLiveData<VacancyState>(VacancyState.Loading)
    val screenState: LiveData<VacancyState> = _screenState
    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite
    private var id = -1

    fun loadVacancy(vacancyId: Int) {
        id = vacancyId
        viewModelScope.launch(Dispatchers.IO) {
            vacancyInteractor.getVacancy(vacancyId).collect { vacancy ->
                _screenState.postValue(VacancyState.Content(vacancy))
            }
        }
    }

}
