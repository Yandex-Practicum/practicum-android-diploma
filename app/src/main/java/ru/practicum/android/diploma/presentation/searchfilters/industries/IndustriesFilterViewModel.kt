package ru.practicum.android.diploma.presentation.searchfilters.industries

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.repository.FiltersInteractor
import ru.practicum.android.diploma.domain.filters.repository.FiltersParametersInteractor
import ru.practicum.android.diploma.domain.models.filters.Industry
import ru.practicum.android.diploma.util.Resource
import java.io.IOException

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
                else -> IndustriesUiState.Empty
            }
        )
    }

    fun search(query: String) {
        try {
            if (industriesList.isEmpty()) {
                _industriesState.postValue(IndustriesUiState.Error)
            }

            val filtered = industriesList.filter {
                it.name.contains(query, ignoreCase = false)
            }

            if (filtered.isEmpty()) {
                _industriesState.postValue(IndustriesUiState.Empty)
            } else {
                _industriesState.postValue(IndustriesUiState.Content(filtered))
            }
        } catch (e: IOException) {
            Log.e("Industry", "Exception search industry")
            _industriesState.postValue(IndustriesUiState.Error)
        }
    }

    fun onClickIndustry(industry: Industry) {
        filterInteractor.selectIndustry(industry.id, industry.name)
    }
}
