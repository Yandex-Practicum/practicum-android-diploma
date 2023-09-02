package ru.practicum.android.diploma.root.data

import ru.practicum.android.diploma.features.filters.domain.models.Filter
import ru.practicum.android.diploma.root.domain.repository.FilterRepository

class FilterImplSharedPreference: FilterRepository {
    override fun saveFilter(model: Filter) {
        TODO("Not yet implemented")
    }

    override fun getFilter(): Filter {//MOCK!
        return Filter(null,null,null,null,null)
    }
}