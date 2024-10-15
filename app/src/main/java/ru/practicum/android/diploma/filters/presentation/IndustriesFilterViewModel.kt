package ru.practicum.android.diploma.filters.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filters.domain.api.FilterIndustriesInteractor
import ru.practicum.android.diploma.filters.domain.models.Industry
import ru.practicum.android.diploma.util.network.HttpStatusCode

class IndustriesFilterViewModel(
    private val interactor: FilterIndustriesInteractor
) : ViewModel() {

    private var stateLiveData = MutableLiveData<FiltersChooserScreenState>()
    fun getStateLiveData(): LiveData<FiltersChooserScreenState> = stateLiveData

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
                        stateLiveData.value = FiltersChooserScreenState.ChooseItem(
                            foundIndustries
                        )
                    } else {
                        stateLiveData.value = FiltersChooserScreenState.Empty
                    }
                }
            }

            HttpStatusCode.NOT_CONNECTED -> stateLiveData.value = FiltersChooserScreenState.NetworkError
            else -> {
                stateLiveData.value = FiltersChooserScreenState.ServerError
            }
        }
    }
}
