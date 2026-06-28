package ru.practicum.android.diploma.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import ru.practicum.android.diploma.domain.api.FilterIndustriesInteractor
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewModel (val industryInteractor: FilterIndustriesInteractor): ViewModel() {

    private val liveData = MutableLiveData<IndustryState>(IndustryState.IsLoading)
    fun observeLiveData(): LiveData<IndustryState> = liveData

    init {
        fill()
    }

    fun fill() {
        viewModelScope.launch {
            industryInteractor.getIndustries()
                .collect { industry -> processResult(industry) }
        }
    }

    fun processResult(playlists: List<Industry>) {
        if (playlists.isEmpty()) {
            renderState(IndustryState.Empty(""))
        } else {
            renderState(IndustryState.Content(playlists))
        }
    }
    fun renderState(state: IndustryState) {
        liveData.postValue(state)
    }
}
