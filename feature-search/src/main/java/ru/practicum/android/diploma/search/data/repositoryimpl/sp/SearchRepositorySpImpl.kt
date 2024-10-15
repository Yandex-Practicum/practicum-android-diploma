package ru.practicum.android.diploma.search.data.repositoryimpl.sp

import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.search.data.repositoryimpl.mappers.SearchMappers
import ru.practicum.android.diploma.search.domain.models.sp.FilterSearch
import ru.practicum.android.diploma.search.domain.repository.SearchRepositorySp

class SearchRepositorySpImpl(
    val filterSp: FilterSp
) : SearchRepositorySp {
    override suspend fun getDataFilter(): FilterSearch {
        return SearchMappers.map(filterSp.getDataFilter())
    }
}
