package ru.practicum.android.diploma.search.data.repositoryimpl.sp

import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.search.data.repositoryimpl.mappers.SearchMappers
import ru.practicum.android.diploma.search.domain.models.sp.FilterSearch
import ru.practicum.android.diploma.search.domain.repository.SearchRepositorySp

internal class SearchRepositorySpImpl(
    private val filterSp: FilterSp
) : SearchRepositorySp {
    override fun getDataFilter(): FilterSearch {
        return SearchMappers.map(filterSp.getDataFilter())
    }

    override fun forceSearch() {
        filterSp.forceSearch()
    }

    override fun getDataFilterBuffer(): FilterSearch {
        return SearchMappers.map(filterSp.getDataFilterBuffer())
    }
}
