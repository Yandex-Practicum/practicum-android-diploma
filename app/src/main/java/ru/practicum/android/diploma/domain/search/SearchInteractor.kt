package ru.practicum.android.diploma.domain.search

import androidx.paging.PagingSource
import ru.practicum.android.diploma.domain.models.Vacancy

interface SearchInteractor {
    fun searchJobs(query: String, pageSize: Int): PagingSource<Int, Vacancy>
}
