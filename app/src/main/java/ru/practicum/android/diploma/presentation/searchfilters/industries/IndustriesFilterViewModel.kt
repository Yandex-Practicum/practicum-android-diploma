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
    private val filterInteractor: FiltersParametersInteractor
) : ViewModel() {

    private val _industriesState = MutableLiveData<IndustriesUiState>()
    val industriesState: MutableLiveData<IndustriesUiState> = _industriesState

    private var industriesList: List<Industry> = emptyList()

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
                    industriesList = resource.data ?: emptyList()
                    IndustriesUiState.Content(resource.data!!)
                }

                is Resource.Error -> {
                    IndustriesUiState.Error
                }
            }
        )
    }

    fun search(query: String) {
        val filtered = if (query.isBlank()) {
            industriesList
        } else {
            industriesList.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        _industriesState.postValue(IndustriesUiState.Content(filtered))
    }

    fun onClickIndustry(industryId: String?, industryName: String?) {
        filterInteractor.selectIndustry(industryId, industryName)
    }
}
