package ru.practicum.android.diploma.ui.filter.industries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.industries.ChildIndustry

class IndustriesFragmentViewModel(
    private val industriesInteractor: IndustriesInteractor
) : ViewModel() {

    private var state = MutableLiveData<IndustriesFragmentUpdate>()
    fun getState(): LiveData<IndustriesFragmentUpdate> = state

    init { getIndustries() }

    private fun getIndustries() {
        viewModelScope.launch {
            industriesInteractor
                .getIndustries()
                .collect { pair ->
                    processResult(
                        pair.first,
                        pair.second
                    )
                }
        }
    }

    private fun processResult(industriesList: List<ChildIndustry>?, errorMessage: Int?) {
        if (errorMessage != null) {
            state.postValue(IndustriesFragmentUpdate.GetIndustriesError)
        } else if (industriesList is List<ChildIndustry>) {
            state.postValue(IndustriesFragmentUpdate.IndustriesList(industriesList))
        }
    }
}
