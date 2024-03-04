package ru.practicum.android.diploma.filter.industry.domain.model

sealed interface IndustryError {
    data object NotFound : IndustryError
    data object GetError : IndustryError
}
