package ru.practicum.android.diploma.ui.industries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.filter.FilterInfoRepository
import ru.practicum.android.diploma.domain.filter.datashared.IndustriesShared
import ru.practicum.android.diploma.domain.industries.ParentIndustriesAllDeal
import ru.practicum.android.diploma.domain.industries.IndustriesInteractor
import ru.practicum.android.diploma.presentation.industries.IndustriesState

class IndustriesViewModel(
    private val interactor: IndustriesInteractor,
    private val filterInfoRepository: FilterInfoRepository
) : ViewModel() {

    private val stateLiveData = MutableLiveData<IndustriesState>()
    fun observeState(): LiveData<IndustriesState> = stateLiveData

//    private val _industries = MutableLiveData<List<ParentIndustriesAllDeal>>()
//    val industries: LiveData<List<ParentIndustriesAllDeal>> get() = _industries

    init {
        loadIndustries()
    }

    fun loadIndustries() {
        viewModelScope.launch {
            interactor.searchIndustries()
                .collect() { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(industriesDetail: List<ParentIndustriesAllDeal>?, errorMessage: Int?) {
        when {
            errorMessage != null -> {
                renderState(
                    IndustriesState.Error(
                        errorMessage = R.string.server_error
                    )
                )
            }

            else -> {
                renderState(
                    IndustriesState.Content(
                        industries = industriesDetail!!
                    )
                )
            }
        }
    }

    fun renderState(industriesState: IndustriesState) {
        stateLiveData.postValue(industriesState)
    }

    fun setIndustriesInfo(industries: IndustriesShared) {
        filterInfoRepository.setIndustriesFlow(industries)
    }
}