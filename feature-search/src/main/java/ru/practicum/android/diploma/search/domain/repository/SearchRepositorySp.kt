package ru.practicum.android.diploma.search.domain.repository

import ru.practicum.android.diploma.search.domain.models.sp.FilterSearch

interface SearchRepositorySp {
    suspend fun getDataFilter(): FilterSearch
}
