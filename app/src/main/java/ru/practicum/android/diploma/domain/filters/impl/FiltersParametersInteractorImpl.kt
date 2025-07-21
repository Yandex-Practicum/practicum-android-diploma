package ru.practicum.android.diploma.domain.filters.impl

import ru.practicum.android.diploma.domain.filters.repository.FilterParametersRepository
import ru.practicum.android.diploma.domain.filters.repository.FiltersParametersInteractor
import ru.practicum.android.diploma.domain.models.filters.FilterParameters

class FiltersParametersInteractorImpl(
    private val repository: FilterParametersRepository
) : FiltersParametersInteractor {

    override fun selectCountry(countryName: String?, countryId: String?) {
        repository.selectCountry(countryName, countryId)
    }

    override fun getSelectedCountryId(): String? {
        return repository.getFiltersParameters().countryId
    }

    override fun selectRegion(regionName: String?, countryName: String?) {
        val currentParams = repository.getFiltersParameters()

        if (!countryName.isNullOrBlank() && currentParams.countryName.isNullOrBlank()) {
            repository.selectCountry(countryName, null)
        }

        repository.selectRegion(regionName)
    }

    override fun selectIndustry(industryId: String?, industryName: String?) {
        repository.selectIndustry(industryId, industryName)
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
