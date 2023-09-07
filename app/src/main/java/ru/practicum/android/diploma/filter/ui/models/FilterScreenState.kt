package ru.practicum.android.diploma.filter.ui.models

sealed interface FilterScreenState {

    object Default: FilterScreenState
    data class Content<T>(val list: List<T>) : FilterScreenState
    object Loading: FilterScreenState
    class NoData<T>(val list: List<T>, val message: String) : FilterScreenState
    class Error(val message: String): FilterScreenState
    class Offline(val message: String): FilterScreenState
}