package ru.practicum.android.diploma.domain.filters.impl

import ru.practicum.android.diploma.domain.filters.repository.FilterParametersRepository
import ru.practicum.android.diploma.domain.filters.repository.FiltersParametersInteractor
import ru.practicum.android.diploma.domain.models.filters.FilterParameters

class FiltersParametersInteractorImpl(
    private val repository: FilterParametersRepository
) : FiltersParametersInteractor {
    override fun selectCountry(countryName: String?) {
        repository.selectCountry(countryName)
    }

    override fun selectRegion(regionName: String?) {
        // ...
    }

    override fun selectIndustry(industryId: String?, industryName: String?) {
        repository.selectIndustry(industryId, industryName)
    }

    override fun defineSalary(value: String?) {
        // ...
    }

    override fun toggleWithoutSalary(enabled: Boolean) {
        // ...
    }

    override fun getFiltersParameters(): FilterParameters {
        return repository.getFiltersParameters()
    }

    override fun clearFilters() {
        repository.clearFilters()
    }
}
