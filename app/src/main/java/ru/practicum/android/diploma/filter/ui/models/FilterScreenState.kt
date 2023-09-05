package ru.practicum.android.diploma.filter.ui.models

import ru.practicum.android.diploma.filter.domain.models.Country

sealed interface FilterScreenState {

    object Default: FilterScreenState
    data class Content(val list: List<Country>) : FilterScreenState
    object Loading: FilterScreenState
    class NoData(val list: List<Country>, val message: String) : FilterScreenState
    class Error(val message: String): FilterScreenState
    class Offline(val message: String): FilterScreenState
}