package ru.practicum.android.diploma.search.presentation

sealed class SearchScreenState {
    object IDLE : SearchScreenState()

    object VACANCY_LIST_LOADED : SearchScreenState()

    object LOADING_NEW_LIST : SearchScreenState()

    object LOADING_NEW_PAGE : SearchScreenState()

    sealed class Error : SearchScreenState() {
        object NO_INTERNET_ERROR : Error()
        object FAILED_TO_FETCH_VACANCIES_ERROR : Error()
        object NEW_PAGE_SERVER_ERROR : Error()
        object NEW_PAGE_NO_INTERNET_ERROR : Error()
    }
}
