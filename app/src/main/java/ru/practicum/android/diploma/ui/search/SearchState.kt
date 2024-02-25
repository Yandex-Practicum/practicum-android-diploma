package ru.practicum.android.diploma.ui.search

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class SearchState {
    object Search : SearchState()
    object Loading : SearchState()
    object Loaded : SearchState()
    object NoInternet : SearchState()
    object FailedToGetList : SearchState()
    object
    data class Content(
        val vacancies: List<Vacancy>,
        val vacanciesNumber: String,
        val isFirstLaunch: Boolean
    ) : SearchState()
    object NoInternetPaging : SearchState()
    data class NextPageLoading(val isLoading: Boolean) : SearchState()

}
