package ru.practicum.android.diploma.search.presentation

internal sealed class SearchScreenState {
    data object Idle : SearchScreenState()

    data object VacancyListLoaded : SearchScreenState()

    data object LoadingNewList : SearchScreenState()

    data object LoadingNewPage : SearchScreenState()

    sealed class Error : SearchScreenState() {
        object ServerError : Error()
        object NoInternetError : Error()
        object FailedToFetchVacanciesError : Error()
        object NewPageServerError : Error()
        object NewPageNoInternetError : Error()
    }
}
