package ru.practicum.android.diploma.ui.industries

import ru.practicum.android.diploma.data.response.Industries

sealed interface IndustriesState {
    data object Loading : IndustriesState

    data class Content(
        val vacancyDetail: List<Industries>
    ) : IndustriesState

    data class Error(
        val errorMessage: Int
    ) : IndustriesState
}
