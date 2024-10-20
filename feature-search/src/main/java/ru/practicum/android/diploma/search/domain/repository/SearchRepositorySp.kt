package ru.practicum.android.diploma.search.domain.repository

import ru.practicum.android.diploma.search.domain.models.sp.FilterSearch

interface SearchRepositorySp {
    fun getDataFilter(): FilterSearch
}
