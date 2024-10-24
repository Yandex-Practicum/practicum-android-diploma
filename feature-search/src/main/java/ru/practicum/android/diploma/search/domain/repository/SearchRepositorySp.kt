package ru.practicum.android.diploma.search.domain.repository

import ru.practicum.android.diploma.search.domain.models.sp.FilterSearch

internal interface SearchRepositorySp {
    fun getDataFilter(): FilterSearch
    fun forceSearch()
    fun getDataFilterBuffer(): FilterSearch
}
