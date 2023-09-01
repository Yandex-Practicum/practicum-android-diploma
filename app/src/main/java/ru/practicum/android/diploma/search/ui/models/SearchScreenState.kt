package ru.practicum.android.diploma.search.ui.models

import ru.practicum.android.diploma.search.domain.models.NetworkError
import ru.practicum.android.diploma.search.domain.models.Vacancy

sealed interface SearchScreenState {
    
    object Default : SearchScreenState
    object Loading : SearchScreenState
    
    data class Content(
        val count: Int,
        val jobList: List<Vacancy>,
    ) : SearchScreenState
    
    data class Error(
        val error: NetworkError,
    ) : SearchScreenState
}