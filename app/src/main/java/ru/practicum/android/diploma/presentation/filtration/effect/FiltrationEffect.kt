package ru.practicum.android.diploma.presentation.filtration.effect

sealed interface FiltrationEffect {
    data object NavigateBack : FiltrationEffect
    data object OpenIndustriesScreen : FiltrationEffect
}
