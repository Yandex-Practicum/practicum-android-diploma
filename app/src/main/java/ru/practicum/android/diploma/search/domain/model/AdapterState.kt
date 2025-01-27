package ru.practicum.android.diploma.search.domain.model

sealed interface AdapterState {
    data object IsLoading : AdapterState
    data object Idle : AdapterState
}
