package ru.practicum.android.diploma.industry.ui

import ru.practicum.android.diploma.core.domain.models.Industries

sealed class IndustryScreenState(var showClearButton: Boolean) {
    object Default : IndustryScreenState(false)
    object Loading : IndustryScreenState(false)
    class Content(
        val industries: Industries
    ) : IndustryScreenState(false)
    class Error(val error: IndustryError) : IndustryScreenState(false)
}

enum class IndustryError {
    INTERNET,
    NOT_FOUND
}
