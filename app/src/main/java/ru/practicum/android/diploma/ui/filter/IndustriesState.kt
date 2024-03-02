package ru.practicum.android.diploma.ui.filter

import ru.practicum.android.diploma.domain.models.Industry

sealed interface IndustriesState {
    object Initial: IndustriesState

    object Loading: IndustriesState

    object Error: IndustriesState

    class Success(val data: List<Industry>): IndustriesState
}
