package ru.practicum.android.diploma.presentation.search

sealed class UiError {
    object NoConnection : UiError()
    object BadRequest : UiError()
    object ServerError : UiError()
}
