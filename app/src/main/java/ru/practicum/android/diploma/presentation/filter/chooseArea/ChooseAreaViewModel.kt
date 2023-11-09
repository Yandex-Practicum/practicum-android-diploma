package ru.practicum.android.diploma.presentation.filter.chooseArea

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.models.filter.Area
import ru.practicum.android.diploma.domain.models.filter.usecase.GetAreasUseCase
import ru.practicum.android.diploma.presentation.filter.chooseArea.state.AreasState
import ru.practicum.android.diploma.util.DataResponse
import ru.practicum.android.diploma.util.NetworkError


class ChooseAreaViewModel(private val areasUseCase: GetAreasUseCase) : ViewModel() {

    //todo замемнить на реальный areaId
    private val areaId = "113"

    private val areasStateLiveData = MutableLiveData<AreasState>()
    fun observeAreasState(): LiveData<AreasState> = areasStateLiveData

    init {
        initScreen()
    }

    private fun initScreen() {
        viewModelScope.launch {
            areasUseCase.invoke(areaId).collect { result ->
                processResult(result)
            }
        }
    }


    private suspend fun processResult(result: DataResponse<Area>) {

        if (result.data != null) {
            areasStateLiveData.value =
                AreasState.DisplayAreas(getAreasList(result.data))
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
        //todo
    }
}