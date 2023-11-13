package ru.practicum.android.diploma.presentation.filter.chooseArea

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.models.filter.Area
import ru.practicum.android.diploma.domain.models.filter.FilterInteractor
import ru.practicum.android.diploma.presentation.filter.chooseArea.state.AreasState
import ru.practicum.android.diploma.util.DataResponse
import ru.practicum.android.diploma.util.NetworkError


class ChooseAreaViewModel(private val areasUseCase: FilterInteractor) : ViewModel() {

    private val areasStateLiveData = MutableLiveData<AreasState>()
    fun observeAreasState(): LiveData<AreasState> = areasStateLiveData

    private val _selectedArea = MutableLiveData<Area?>()

    val selectedArea: LiveData<Area?> = _selectedArea

    // Добавленное поле для хранения отфильтрованных регионов
    private var filteredAreas: ArrayList<Area> = arrayListOf()

    init {
        initScreen()
    }

    fun initScreen() {
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
            areasStateLiveData.value = AreasState.DisplayAreas(filteredAreas)
        } else {
            val filteredList = filteredAreas.filter { area ->
                area.name.contains(query, ignoreCase = true)
            }.toMutableList()
            areasStateLiveData.value = AreasState.DisplayAreas(ArrayList(filteredList))
        }
    }


    private suspend fun processResult(result: DataResponse<Area>) {

        if (result.data != null) {
            areasStateLiveData.value =
                AreasState.DisplayAreas(getAreasList(result.data))
            Log.e("ChooseAreaViewModel", "Result: $result")

        } else {
            when (result.networkError!!) {
                NetworkError.BAD_CONNECTION.toString() -> areasStateLiveData.value =
                    AreasState.Error("Проверьте подключение к интернету")

                NetworkError.SERVER_ERROR.toString() -> areasStateLiveData.value =
                    AreasState.Error("Ошибка сервера")
            }
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

    private fun loadSelectedCountryId() : String? {
        val selectedCountry = areasUseCase.getSelectedCountry()
        return selectedCountry?.id
    }


    fun loadSelectedArea() {
        _selectedArea.value = areasUseCase.getSelectedArea()
    }

}