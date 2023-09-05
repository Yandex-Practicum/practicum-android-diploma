package ru.practicum.android.diploma.details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.details.domain.VacancyInteractor
import ru.practicum.android.diploma.details.domain.models.VacancyDetails
import ru.practicum.android.diploma.search.data.ResourceProvider

class VacancyViewModel(
    private val vacancyInteractor: VacancyInteractor,
    private val resourceProvider: ResourceProvider,
): ViewModel() {

    private val _state = MutableLiveData<VacancyState>()
    val state: LiveData<VacancyState> = _state

    fun loadVacancyDetails(vacancyId: String){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                val result = vacancyInteractor.loadVacancyDetails(vacancyId)
                processResult(result.first, result.second)
            }
        }
    }

    private fun processResult(vacancyDetails: VacancyDetails?, errorMessage: String?) {
        when {
            errorMessage != null -> {
                _state.postValue(VacancyState.Error(resourceProvider.getString(R.string.no_connection)))
            }

            else -> {
                _state.postValue(VacancyState.Content(vacancyDetails!!))
            }
        }
    }
}