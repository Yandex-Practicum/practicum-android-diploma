package ru.practicum.android.diploma.presentation.searchfilters.industries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.repository.FiltersInteractor
import ru.practicum.android.diploma.domain.filters.repository.FiltersParametersInteractor
import ru.practicum.android.diploma.domain.models.filters.Industry
import ru.practicum.android.diploma.util.Resource

class IndustriesFilterViewModel(
    private val interactor: FiltersInteractor,
    private val filterInteractor: FiltersParametersInteractor) : ViewModel() {
    private val _industriesState = MutableLiveData<IndustriesUiState>()
    val industriesState: MutableLiveData<IndustriesUiState> = _industriesState

    init {
        getIndustries()
    }

    private fun getIndustries() {
        viewModelScope.launch {
            _industriesState.postValue(IndustriesUiState.Loading)
            interactor.getIndustries()
                .collect {
                    stateIndustry(it)
                }
        }
    }

    private fun stateIndustry(resource: Resource<List<Industry>>) {
        _industriesState.postValue(
            when (resource) {
                is Resource.Success -> {
                    IndustriesUiState.Content(resource.data!!)
                }

                is Resource.Error -> {
                    IndustriesUiState.Error
                }
            }
        )
    }

    fun onClick(industryId: String?, industryName: String?) {
        filterInteractor.selectIndustry(industryId, industryName)
    }
}
