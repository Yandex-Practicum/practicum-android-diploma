package ru.practicum.android.diploma.filters.industries.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filters.industries.domain.api.FilterIndustriesInteractor
import ru.practicum.android.diploma.filters.industries.domain.models.Industry
import ru.practicum.android.diploma.util.network.HttpStatusCode

class IndustrySelectViewModel(
    private val interactor: FilterIndustriesInteractor
) : ViewModel() {

    private var stateLiveData = MutableLiveData<IndustrySelectScreenState>()
    fun getStateLiveData(): LiveData<IndustrySelectScreenState> = stateLiveData

    init {
        loadIndustries()
    }

    fun loadIndustries() {
        viewModelScope.launch {
            interactor.getIndustries()
                .collect { result ->
                    processingState(result.first, result.second)
                }
        }
    }

    private fun processingState(foundIndustries: List<Industry>?, errorMessage: HttpStatusCode?) {
        when (errorMessage) {
            HttpStatusCode.OK -> {
                if (foundIndustries != null) {
                    if (foundIndustries.isNotEmpty()) {
                        stateLiveData.value = IndustrySelectScreenState.ChooseItem<Industry>(
                            foundIndustries
                        )
                    } else {
                        stateLiveData.value = IndustrySelectScreenState.Empty
                    }
                }
            }

            HttpStatusCode.NOT_CONNECTED -> stateLiveData.value = IndustrySelectScreenState.NetworkError
            else -> {
                stateLiveData.value = IndustrySelectScreenState.ServerError
            }
        }
    }
}
