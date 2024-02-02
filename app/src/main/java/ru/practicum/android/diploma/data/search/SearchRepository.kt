package ru.practicum.android.diploma.data.search

import androidx.paging.PagingSource
import ru.practicum.android.diploma.domain.models.Vacancy

interface SearchRepository {
    fun searchJobs(query: String, pageSize: Int): PagingSource<Int, Vacancy>
}
