package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.filter.FilterInteractor
import ru.practicum.android.diploma.domain.models.filter.Industry
import ru.practicum.android.diploma.presentation.filter.chooseArea.state.IndustriesState
import ru.practicum.android.diploma.util.DataResponse
import ru.practicum.android.diploma.util.NetworkError

class SelectIndustryViewModel(private val filterInteractor: FilterInteractor) : ViewModel() {


    private val industriesStateLiveData = MutableLiveData<IndustriesState>()

    private val _selectedIndustry = MutableLiveData<Industry?>()

    fun observeIndustriesState(): LiveData<IndustriesState> = industriesStateLiveData


    private var filteredIndustries: ArrayList<Industry> = arrayListOf()

    init {

        initScreen()
    }
    fun initScreen() {
        viewModelScope.launch {
            filterInteractor.getIndustries().collect { result ->
                processResult(result)
            }
        }
    }

    fun filterIndustries(query: String) {
        if (query.isEmpty()) {
            industriesStateLiveData.value = IndustriesState.DisplayIndustries(filteredIndustries)
        } else {
            val filteredList = filteredIndustries.filter { industry ->
                industry.name.contains(query, ignoreCase = true)
            }.toMutableList()
            industriesStateLiveData.value =
                IndustriesState.DisplayIndustries(ArrayList(filteredList))
        }
    }

    private suspend fun processResult(result: DataResponse<Industry>) {
        if (result.data != null) {
            industriesStateLiveData.value =
                IndustriesState.DisplayIndustries(getFullIndustriesList(result.data))
        } else {
            when (result.networkError!!) {
                NetworkError.BAD_CONNECTION.toString() -> industriesStateLiveData.value =
                    IndustriesState.Error("Проверьте подключение к интернету")

                NetworkError.SERVER_ERROR.toString() -> industriesStateLiveData.value =
                    IndustriesState.Error("Ошибка сервера")
            }
        }
    }

    private suspend fun getFullIndustriesList(nestedIndustriesList: List<Industry>): ArrayList<Industry> =
        withContext(Dispatchers.Default) {
            val extendedIndustriesList: ArrayList<Industry> = arrayListOf()
            for (industry in nestedIndustriesList) {
                extendedIndustriesList.add(industry)
            }
            extendedIndustriesList.sortBy { it.name }
            extendedIndustriesList
        }

    fun onIndustryClicked(industry: Industry) {
        filterInteractor.setSelectedIdustries(industry)
    }

    fun loadSelectedIndustry() {
        _selectedIndustry.value = filterInteractor.getSelectedIndustries()
    }
}
