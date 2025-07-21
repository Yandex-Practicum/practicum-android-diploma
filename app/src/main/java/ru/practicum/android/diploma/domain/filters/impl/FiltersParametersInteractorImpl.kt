package ru.practicum.android.diploma.domain.filters.impl

import ru.practicum.android.diploma.domain.filters.repository.FilterParametersRepository
import ru.practicum.android.diploma.domain.filters.repository.FiltersParametersInteractor
import ru.practicum.android.diploma.domain.models.filters.FilterParameters

class FiltersParametersInteractorImpl(
    private val repository: FilterParametersRepository
) : FiltersParametersInteractor {
    private var selectedCountryId: String? = null

    override fun selectCountry(countryName: String?, countryId: String?) {
        selectedCountryId = countryId
        repository.selectCountry(countryName)
    }

    override fun getSelectedCountryId(): String? {
        return selectedCountryId
    }

    override fun selectRegion(regionName: String?, countryName: String?) {
        repository.selectRegion(regionName)
    }

    override fun selectIndustry(industryName: String?) {
        TODO("Not yet implemented")
    }

    override fun defineSalary(value: String?) {
        TODO("Not yet implemented")
    }

    override fun toggleWithoutSalary(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getFiltersParameters(): FilterParameters {
        return repository.getFiltersParameters()
    }

    override fun clearFilters() {
        repository.clearFilters()
    }
}
