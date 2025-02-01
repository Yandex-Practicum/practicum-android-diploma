package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.util.RegionConverter
import ru.practicum.android.diploma.common.util.debounce
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.filter.domain.model.City
import ru.practicum.android.diploma.filter.domain.model.Industry
import ru.practicum.android.diploma.filter.domain.model.RegionViewState

class RegionFilterViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    private var selectedCity: City? = null
    private val filter = Filter(region = 113)
    private val stateLiveData = MutableLiveData<RegionViewState>()
    fun observeState(): LiveData<RegionViewState> = stateLiveData

    private var lastSearchText: String? = null

    private val regionsSearchDebounce = debounce<String>(
        SEARCH_DEBOUNCE_DELAY,
        viewModelScope,
        true
    ) { changedText ->
        getAllRegions(changedText)
    }

    fun searchDebounce(changedText: String) {
        if (lastSearchText != changedText) {
            lastSearchText = changedText
            regionsSearchDebounce(changedText)
        }
    }
    private fun getAllRegions(query: String) { // получить список всех регионов
        val lowerCaseQuery = query.lowercase()
        renderState(RegionViewState.Loading)
        when(filter.region) {
            -1 -> viewModelScope.launch {
                filterInteractor.getAllRegions().collect { state ->
                    if(state is RegionViewState.Success){
                        val nonCisList = RegionConverter.mapNonCisRegions(state.areas)
                        val filteredRegions = nonCisList.filterNot { region ->
                            region.name.lowercase().contains(lowerCaseQuery)
                        }
                        if (filteredRegions.isNotEmpty()) {
                            // Обновляем состояние с отфильтрованным списком
                            renderState(RegionViewState.Success(filteredRegions))
                        } else {
                            renderState(RegionViewState.NotFoundError)
                        }
                    }

                }
            }
            null -> viewModelScope.launch {
                filterInteractor.getAllRegions().collect { state ->
                    if(state is RegionViewState.Success){
                        val cisList = RegionConverter.mapAllCisRegions(state.areas)
                        val filteredRegions = cisList.filterNot { region ->
                            region.name.lowercase().contains(lowerCaseQuery)
                        }
                        if (filteredRegions.isNotEmpty()) {
                            // Обновляем состояние с отфильтрованным списком
                            renderState(RegionViewState.Success(filteredRegions))
                        } else {
                            renderState(RegionViewState.NotFoundError)
                        }
                    }

                }
            }
            else -> viewModelScope.launch {
                filterInteractor.searchRegionsById(filter.region).collect { state ->
                    if(state is RegionViewState.Success){
                        val regions = RegionConverter.mapRegions(state.areas)
                        val filteredRegions = regions.filterNot { region ->
                            region.name.lowercase().contains(lowerCaseQuery)
                        }
                        if (filteredRegions.isNotEmpty()) {
                            // Обновляем состояние с отфильтрованным списком
                            renderState(RegionViewState.Success(filteredRegions))
                        } else {
                            renderState(RegionViewState.NotFoundError)
                        }
                    }

                }
            }

//            viewModelScope.launch {
//                filterInteractor
//                    .getAllRegions()
//                    .collect { viewState ->
//                        when (viewState) {
//                            is RegionViewState.Success -> {
//                                proceedSuccessState(viewState, lowerCaseQuery)
////                            // Фильтруем список городов
////                            val filteredRegions = viewState.areas.filterNot { region ->
////                                region.name.lowercase().contains(lowerCaseQuery)
////                            }
////                            if (filteredRegions.isNotEmpty()) {
////                                // Обновляем состояние с отфильтрованным списком
////                                renderState(RegionViewState.Success(filteredRegions))
////                            } else {
////                                renderState(RegionViewState.NotFoundError)
////                            }
//                            }
//
//                            else -> {
//                                // Если состояние не Success, просто рендерим его
//                                renderState(viewState)
//                            }
//                        }
//                    }
//            }
        }
    }


    private fun proceedSuccessState(viewState:RegionViewState.Success, query: String){
        // Фильтруем список городов
        val filteredRegions = viewState.areas.filterNot { region ->
            region.name.lowercase().contains(query)
        }
        if (filteredRegions.isNotEmpty()) {
            // Обновляем состояние с отфильтрованным списком
            renderState(RegionViewState.Success(filteredRegions))
        } else {
            renderState(RegionViewState.NotFoundError)
        }
    }


    private fun renderState(state: RegionViewState) {
        stateLiveData.postValue(state)
    }
    fun setIndustry(city: City) {
        selectedCity = city.copy(isSelected = false)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 300L
    }
}

data class Filter(
    val country: String? = null,
    val region: Int? = null,
    val industry: Industry? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
)

