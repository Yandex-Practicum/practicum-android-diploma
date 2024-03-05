package ru.practicum.android.diploma.filter.area.domain.model

sealed interface AreaError {
    data object NotFound : AreaError
    data object GetError : AreaError
}
