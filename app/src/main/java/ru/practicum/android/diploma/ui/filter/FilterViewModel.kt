package ru.practicum.android.diploma.ui.filter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FilterPreferences
import ru.practicum.android.diploma.domain.filters.AreasInteractor
import ru.practicum.android.diploma.domain.filters.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.Areas
import ru.practicum.android.diploma.domain.models.Industries
import ru.practicum.android.diploma.ui.filter.industry.IndustryListItem
import ru.practicum.android.diploma.ui.filter.industry.IndustryState
import ru.practicum.android.diploma.ui.filter.industry.toIndustryListItems
import ru.practicum.android.diploma.ui.filter.model.FilterScreenState
import ru.practicum.android.diploma.ui.filter.model.SelectedFilters
import ru.practicum.android.diploma.util.HH_LOG

class FilterViewModel(
    private val interactorAreas: AreasInteractor,
    private val interactorIndustries: IndustriesInteractor,
    private val filterPreferences: FilterPreferences
) : ViewModel() {

    private val state = MutableLiveData<FilterScreenState>()
    fun getState(): LiveData<FilterScreenState> = state



    var testModel = SelectedFilters("Россия, Москва", "IT", 999999, true)

    fun getFilters() {
        // Тут мы достаем сохраненные в SP фильтры
        testModel = filterPreferences.loadFilters() ?: SelectedFilters("Россия, Владикавказ", "IT", 999999, true)
        state.postValue(FilterScreenState.CONTENT(testModel))
    }

    fun clearPlace() {
        // TODO
        testModel = SelectedFilters(null, testModel.industry, testModel.salary, testModel.onlyWithSalary)
        state.postValue(FilterScreenState.CONTENT(testModel))
    }

    fun clearIndustry() {
        // TODO
        testModel = SelectedFilters(testModel.place, null, testModel.salary, testModel.onlyWithSalary)
        state.postValue(FilterScreenState.CONTENT(testModel))
    }

    fun clearSalary() {
        // TODO
        testModel = SelectedFilters(testModel.place, testModel.industry, null, testModel.onlyWithSalary)
        state.postValue(FilterScreenState.CONTENT(testModel))
    }

    fun setShowNoSalary() {
        testModel = SelectedFilters(testModel.place, testModel.industry, testModel.salary, !testModel.onlyWithSalary)
        state.postValue(FilterScreenState.CONTENT(testModel))
    }

    fun clearFilters() {
        // временно - проверить работу шП
        filterPreferences.clearFilters()
        testModel = SelectedFilters(null, null, null, false)
        state.postValue(FilterScreenState.CONTENT(testModel))
    }

    fun saveFilters() {
        // временно - проверить работу шП
        filterPreferences.saveFilters(testModel)
    }

    // Это тестовый запрос
    fun getAreas() {
        viewModelScope.launch {
            interactorAreas.getAreas().collect { pair ->
                processAreasResult(pair.first, pair.second)
            }
        }
    }


    // Обработка ответа
    private fun processAreasResult(areas: List<Areas>?, error: Int?) {
        if (areas != null) {
            printAreas(areas)
        }
        if (error != null) {
            Log.d(HH_LOG, "Error: $error")
        }
    }

    private fun printAreas(areas: List<Areas>, sep: String = "") {
        for (area in areas) {
            Log.d(HH_LOG, "$sep Areas ID: ${area.id}; Areas name: ${area.name}")
            printAreas(area.areas, "$sep    ")
        }
    }
}
