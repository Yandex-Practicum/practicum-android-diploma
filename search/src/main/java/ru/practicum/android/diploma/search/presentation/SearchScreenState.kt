package ru.practicum.android.diploma.search.presentation

sealed class SearchScreenState {
    data object IDLE: SearchScreenState()

    data object LOADING_NEW_LIST: SearchScreenState()

    data object LOADING_NEW_PAGE: SearchScreenState()

    data object NO_INTERNET_ERROR: SearchScreenState()

    data object FAILED_TO_FETCH_VACANCIES_ERROR: SearchScreenState()

    data object VACANCY_LIST_LOADED: SearchScreenState()
}
