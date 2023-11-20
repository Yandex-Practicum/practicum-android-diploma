package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.ResourceProvider
import ru.practicum.android.diploma.domain.filter.FilterInteractor
import ru.practicum.android.diploma.domain.models.filter.Industry
import ru.practicum.android.diploma.presentation.filter.selectArea.state.IndustriesState
import ru.practicum.android.diploma.util.DataResponse

class SelectIndustryViewModel(
    private val filterInteractor: FilterInteractor,
    val resourceProvider: ResourceProvider
) : ViewModel() {


    private val industriesStateLiveData = MutableLiveData<IndustriesState>()
    private val selectedIndustry = MutableLiveData(mutableListOf<Industry>())
    fun observeSelectedIndustry(): LiveData<MutableList<Industry>> = selectedIndustry

    fun observeIndustriesState(): LiveData<IndustriesState> = industriesStateLiveData
    private var filteredIndustries: List<Industry> = mutableListOf()
    private val filteredIndustriesState = MutableLiveData<IndustriesState>()
    fun observeFilteredIndustriesState(): LiveData<IndustriesState> = filteredIndustriesState

    init {

        initScreen()
    }

    private fun initScreen() {
        viewModelScope.launch {
            filterInteractor.getIndustries().collect { result ->
                processResult(result)
            }
        }
    }

    fun filterIndustries(query: String) {
        if (query.isEmpty()) {
            filteredIndustriesState.value = IndustriesState.DisplayIndustries(filteredIndustries)
        } else {
            val filteredList = filteredIndustries.filter { industry ->
                industry.name.contains(query, ignoreCase = true)
            }.toMutableList()
            if (filteredList.isEmpty()) filteredIndustriesState.postValue(
                IndustriesState.Error(
                    resourceProvider.getString(R.string.empty_filtered_industry)
                )
            ) else
                filteredIndustriesState.postValue(
                    IndustriesState.DisplayIndustries(
                        ArrayList(
                            filteredList
                        )
                    )
                )

        }
    }

    private suspend fun processResult(result: DataResponse<Industry>) {
        if (result.data != null) {
            filteredIndustries = getFullIndustriesList(result.data)
            industriesStateLiveData.value =
                if (filteredIndustries.isNotEmpty()) IndustriesState.DisplayIndustries(
                    filteredIndustries
                ) else IndustriesState.Error(
                    resourceProvider.getString(
                        R.string.no_list
                    )
                )

        } else {
            IndustriesState.Error(resourceProvider.getString(R.string.no_list))
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

    fun onIndustryClicked(industry: Industry, isChecked: Boolean) {
        val changedList = selectedIndustry.value ?: mutableListOf()
        if (isChecked) changedList.add(industry)
        else changedList.remove(industry)
        selectedIndustry.postValue(changedList)
    }

    fun setIndustries() {
        filterInteractor.setSelectedIndustries(selectedIndustry.value)
    }

    fun loadSelectedIndustry() {
        selectedIndustry.postValue(
            (filterInteractor.getSelectedIndustries() ?: mutableListOf()).toMutableList()
        )
    }

}
