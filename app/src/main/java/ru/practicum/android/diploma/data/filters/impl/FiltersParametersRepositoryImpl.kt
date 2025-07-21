package ru.practicum.android.diploma.data.filters.impl

import ru.practicum.android.diploma.data.filters.storage.api.FilterParametersStorage
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.mappers.toDto
import ru.practicum.android.diploma.domain.filters.repository.FilterParametersRepository
import ru.practicum.android.diploma.domain.models.filters.FilterParameters

class FiltersParametersRepositoryImpl(private val storage: FilterParametersStorage) : FilterParametersRepository {
    override fun selectCountry(countryName: String?, countryId: String?) {
        val selected = storage.getFilterParameters()
            .toDomain()
            .copy(countryName = countryName, countryId = countryId)
        storage.putFilterParameters(selected.toDto())
    }

    override fun selectRegion(regionName: String?) {
        val selected = storage.getFilterParameters()
            .toDomain()
            .copy(regionName = regionName)
        storage.putFilterParameters(selected.toDto())
    }

    override fun selectIndustry(industryName: String?) {
        // ...
    }

    override fun defineSalary(value: String?) {
        // ...
    }

    override fun toggleWithoutSalary(enabled: Boolean) {
        // ...
    }

    override fun getFiltersParameters(): FilterParameters {
        return storage.getFilterParameters().toDomain()
    }

    override fun clearFilters() {
        storage.removeFilterParameters()
    }
}
