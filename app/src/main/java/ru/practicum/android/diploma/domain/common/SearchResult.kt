package ru.practicum.android.diploma.domain.common

import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.CountryRegionData
import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface SearchResult {

    data object Empty : SearchResult
    data object Loading : SearchResult
    data object PaginationLoading : SearchResult
    data object NotFound : SearchResult
    data object NoConnection : SearchResult

    data class SearchVacanciesContent(
        val found: Int,
        val items: List<Vacancy>
    ) : SearchResult

    data class GetVacancyContent(
        val vacancy: Vacancy
    ) : SearchResult

    data class GetAreasContent(
        val list: List<Area>
    ) : SearchResult

    data class GetPlacesContent(
        val regions: List<CountryRegionData>
    ) : SearchResult

    data object Error : SearchResult
}
