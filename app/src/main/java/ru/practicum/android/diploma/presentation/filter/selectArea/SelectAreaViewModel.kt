package ru.practicum.android.diploma.presentation.filter.selectArea

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
import ru.practicum.android.diploma.domain.models.filter.Area
import ru.practicum.android.diploma.presentation.filter.selectArea.state.AreasState
import ru.practicum.android.diploma.util.DataResponse


class SelectAreaViewModel(
    private val areasUseCase: FilterInteractor,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val areasStateLiveData = MutableLiveData<AreasState>()
    fun observeAreasState(): LiveData<AreasState> = areasStateLiveData
    private val areasFilterStateLiveData = MutableLiveData<AreasState>()
    fun observeFilterAreasState(): LiveData<AreasState> = areasFilterStateLiveData

    private val selectedArea = MutableLiveData<Area?>()

    // Добавленное поле для хранения отфильтрованных регионов
    private var filteredAreas: ArrayList<Area> = arrayListOf()

    init {
        initScreen()
    }

    private fun initScreen() {
        viewModelScope.launch {
            loadSelectedCountryId()?.let {
                areasUseCase.getAreas(it).collect { result ->
                    processResult(result)
                }
            }
        }
    }

    fun filterAreas(query: String) {
        if (query.isEmpty()) {
            areasFilterStateLiveData.value = AreasState.DisplayAreas(filteredAreas)
        } else {
            val filteredList = filteredAreas.filter { area ->
                area.name.contains(query, ignoreCase = true)
            }.toMutableList()
            if (filteredList.isEmpty()) areasFilterStateLiveData.postValue(
                AreasState.Error(
                    resourceProvider.getString(R.string.empty_filtered_regions)
                )
            )
            else
                areasFilterStateLiveData.postValue(AreasState.DisplayAreas(ArrayList(filteredList)))
        }
    }


    private suspend fun processResult(result: DataResponse<Area>) {
        if (result.data != null) {
            filteredAreas = getAreasList(result.data)
            if (filteredAreas.isNotEmpty()) areasStateLiveData.value =
                AreasState.DisplayAreas(filteredAreas) else AreasState.Error(
                resourceProvider.getString(
                    R.string.no_list
                )
            )
        } else {
            areasStateLiveData.value =
                AreasState.Error(resourceProvider.getString(R.string.no_list))
        }
    }

    private suspend fun getAreasList(nestedAreasList: List<Area>): ArrayList<Area> =
        withContext(Dispatchers.Default) {
            val extendedAreasList: ArrayList<Area> = arrayListOf()
            for (area in nestedAreasList) {
                getAreasRecursively(extendedAreasList, area)
            }
            extendedAreasList.sortBy { it.name }
            extendedAreasList
        }


    private fun getAreasRecursively(extendedAreasList: ArrayList<Area>, area: Area) {
        extendedAreasList.add(area)
        if (area.areas.isNotEmpty()) {
            for (nestedArea in area.areas) {
                getAreasRecursively(extendedAreasList, nestedArea)
            }
        }
    }

    fun onAreaClicked(area: Area) {
        areasUseCase.setSelectedArea(area)
    }

    private fun loadSelectedCountryId(): String? {
        val selectedCountry = areasUseCase.getSelectedCountry()
        return selectedCountry?.id
    }

    fun loadSelectedArea() {
        selectedArea.value = areasUseCase.getSelectedArea()
    }


}