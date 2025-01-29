package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.filter.domain.model.IndustryViewState

class FilterScreenViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel() {
    private val stateLiveData = MutableLiveData<IndustryViewState>()
    fun observeState(): LiveData<IndustryViewState> = stateLiveData

    fun getIndustries() {
        renderState(IndustryViewState.Loading)
        viewModelScope.launch {
            filterInteractor
                .getIndustries()
                .collect { viewState ->
                    renderState(viewState) }
        }
    }

    private fun renderState(state: IndustryViewState) {
        stateLiveData.postValue(state)
    }
}
