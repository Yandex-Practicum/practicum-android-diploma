package ru.practicum.android.diploma.search.presentation

internal sealed class SearchScreenState {
    data object Idle : SearchScreenState()

    data object VacancyListLoaded : SearchScreenState()

    data object LoadingNewList : SearchScreenState()

    data object LoadingNewPage : SearchScreenState()

    sealed class Error : SearchScreenState() {
        data object ServerError : Error()
        data object NoInternetError : Error()
        data object FailedToFetchVacanciesError : Error()
        data object NewPageServerError : Error()
        data object NewPageNoInternetError : Error()
    }
}
