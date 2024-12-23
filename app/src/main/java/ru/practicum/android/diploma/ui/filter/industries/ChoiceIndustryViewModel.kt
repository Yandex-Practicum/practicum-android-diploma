package ru.practicum.android.diploma.ui.filter.industries

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.dto.model.industries.IndustriesFullDto
import ru.practicum.android.diploma.domain.industries.IndustriesInteractor

class ChoiceIndustryViewModel(
    application: Application,
    private val interactor: IndustriesInteractor
) : AndroidViewModel(application) {

    private val _industriesState = MutableLiveData<IndustriesState>()
    val industriesState: LiveData<IndustriesState> get() = _industriesState

    fun showIndustries() {
        viewModelScope.launch {
            interactor
                .getIndustries()
                .collect { industry ->
                    processResult(industry)
                }
        }
    }

    private fun processResult(
        result:
        List<IndustriesFullDto?>?
    ) {
        renderState(IndustriesState.FoundIndustries(result))
    }

    private fun renderState(state: IndustriesState) {
        _industriesState.postValue(state)
    }
}

