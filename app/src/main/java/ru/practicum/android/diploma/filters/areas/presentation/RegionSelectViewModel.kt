package ru.practicum.android.diploma.filters.areas.presentation

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filters.areas.domain.api.SearchRegionsByNameInteractor
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.search.domain.models.VacancyListResponseData

class RegionSelectViewModel(
    private val interactor: SearchRegionsByNameInteractor
) : ViewModel() {

    private var latestSearchText: String? = null
    private var regionsSearchData: VacancyListResponseData? = null
    private val currentRegionList = mutableListOf<Area>()

    fun findRegions(text: String) {
        // ищет в списке регион похожий на введённый текст
    }

    fun loadRegions() {
        // загружает список всех регионов
    }

    fun finishSelect() {
        // заканчивает выбор региона и сохраняет его в sharedPreferences
    }
}
