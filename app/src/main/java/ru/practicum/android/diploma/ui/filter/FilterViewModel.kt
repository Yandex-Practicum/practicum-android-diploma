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

    private val _industryState = MutableLiveData<IndustryState>()
    val industryState: LiveData<IndustryState> = _industryState

    private val state = MutableLiveData<FilterScreenState>()
    fun getState(): LiveData<FilterScreenState> = state

    private var fullIndustryList: List<IndustryListItem> = emptyList()

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

    // Это тестовый запрос
    fun getIndustries() {
        _industryState.postValue(IndustryState.LOADING)
        viewModelScope.launch {
            interactorIndustries.getIndustries().collect { pair ->

                processIndustriesResult(pair.first, pair.second)
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

    // Это тестовый вывод в лог!
    private fun processIndustriesResult(industries: List<Industries>?, error: Int?) {
        if (industries != null) {
            val industryListItems = industries.toIndustryListItems()
            fullIndustryList = industryListItems
            _industryState.postValue(IndustryState.CONTENT(industryListItems))
        } else {
            _industryState.postValue(IndustryState.EMPTY)
        }
        if (error != null) {
            _industryState.postValue(IndustryState.ERROR(error))
        }
    }

    fun selectIndustry(industryId: String, currentQuery: String) {
        fullIndustryList = fullIndustryList.map {
            if (it.id == industryId) {
                it.copy(isSelected = true)
            } else {
                it.copy(isSelected = false)
            }
        }

        filterIndustries(currentQuery)
    }

    fun filterIndustries(query: String) {
        val filteredList = fullIndustryList.filter { item ->
            item.name.contains(query.trim(), ignoreCase = true)
        }
        _industryState.postValue(IndustryState.CONTENT(filteredList))
    }

    fun saveSelectedIndustry() {
        val selectedIndustry = industryState.value?.let { state ->
            (state as? IndustryState.CONTENT)?.industryListItems?.find { it.isSelected }
        }
        // в этом методе обновляем состояние экрана или модели или того и другого
        selectedIndustry?.let {
            testModel = testModel.copy(industry = it.name)
            Log.d("Industry", "testmodel.industry: ${testModel.industry}")
            state.postValue(FilterScreenState.CONTENT(testModel))
            filterPreferences.saveFilters(testModel)
        }

        selectedIndustry?.let {
            Log.d("Industry", "Selected Industry: ${it.name}")
        }
    }

    private fun printAreas(areas: List<Areas>, sep: String = "") {
        for (area in areas) {
            Log.d(HH_LOG, "$sep Areas ID: ${area.id}; Areas name: ${area.name}")
            printAreas(area.areas, "$sep    ")
        }
    }
}
