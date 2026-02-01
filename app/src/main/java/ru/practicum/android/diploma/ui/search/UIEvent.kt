package ru.practicum.android.diploma.ui.search

sealed class UIEvent {
    object ShowNoInternetToast : UIEvent()
    object ShowGenericErrorToast : UIEvent()
}
