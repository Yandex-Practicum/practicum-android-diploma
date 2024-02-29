package ru.practicum.android.diploma.ui.filter

import ru.practicum.android.diploma.data.response.IndustryResponse

sealed interface IndustriesState {
    object Initial: IndustriesState

    object Loading: IndustriesState

    object Error: IndustriesState

    class Success(val data: List<IndustryResponse>): IndustriesState
}
