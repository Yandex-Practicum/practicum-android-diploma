package ru.practicum.android.diploma.domain.filters.repository

import ru.practicum.android.diploma.domain.models.filters.FilterParameters

interface FiltersParametersInteractor {
    fun selectCountry(countryName: String?, countryId: String?)
    fun getSelectedCountryId(): String?
    fun selectRegion(regionName: String?, countryName: String? = null)
    fun selectIndustry(industryId: String?, industryName: String?)
    fun defineSalary(value: String?)
    fun toggleWithoutSalary(enabled: Boolean)
    fun getFiltersParameters(): FilterParameters
    fun clearFilters()
}
