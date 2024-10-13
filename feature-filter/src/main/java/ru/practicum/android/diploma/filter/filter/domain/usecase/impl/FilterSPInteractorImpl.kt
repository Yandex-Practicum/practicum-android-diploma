package ru.practicum.android.diploma.filter.filter.domain.usecase.impl

import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository

class FilterSPInteractorImpl(private val repository: FilterSPRepository) : FilterSPInteractor {
    override fun putValue(key: String, value: String): Boolean {
        return repository.putValue(key, value)
    }

    override fun getValue(key: String): String? {
        return repository.getValue(key)
    }

    override fun getAll(): Map<String, String> {
        return repository.getAll()
    }
}
