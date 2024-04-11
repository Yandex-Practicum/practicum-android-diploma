package ru.practicum.android.diploma.domain.api.search

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

interface SearchPagingRepository {
    fun getSearchPaging(query: String, filters: Filters): Flow<PagingData<Vacancy>>
}
