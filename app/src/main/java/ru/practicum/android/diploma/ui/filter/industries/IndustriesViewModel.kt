package ru.practicum.android.diploma.ui.filter.industries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.filter.FilterRepositoryIndustriesFlow
import ru.practicum.android.diploma.domain.filter.datashared.IndustriesShared
import ru.practicum.android.diploma.domain.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.industries.ChildIndustry

class IndustriesViewModel(
    private val industriesInteractor: IndustriesInteractor,
    private val filterRepositoryIndustriesFlow: FilterRepositoryIndustriesFlow
) : ViewModel() {

    private var _state = MutableLiveData<IndustriesState>()
    fun getState(): LiveData<IndustriesState> = _state

    init {
        getIndustriesAndLoadFilteredIndustry()
    }

    private fun getIndustriesAndLoadFilteredIndustry() {
        _state.postValue(IndustriesState.Loading)
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
            _state.postValue(IndustriesState.Error(
                errorMessage = R.string.server_error
                )
            )
        } else if (industriesList is List<ChildIndustry>) {
            _state.postValue(IndustriesState.IndustriesList(
                industriesList.map {
                    ChildIndustryWithSelection(
                        id = it.id,
                        name = it.name,
                        selected = false
                    )
                }
            ))
        }
    }

    fun saveFilteredIndustry(industry: ChildIndustryWithSelection) {
        filterRepositoryIndustriesFlow.setIndustriesFlow(
            IndustriesShared(
                industriesId = industry.id,
                industriesName = industry.name
            )
        )
    }

    fun applyFilters() {
        viewModelScope.launch {
            filterRepositoryIndustriesFlow.getIndustriesFlow().collect { industryShared ->
                if (industryShared is IndustriesShared) {
                    _state.postValue(
                        IndustriesState.FilteredIndustry(
                            ChildIndustryWithSelection(
                                id = industryShared.industriesId ?: "",
                                name = industryShared.industriesName ?: "",
                                selected = true
                            )
                        )
                    )
                }
            }
        }
    }
}
