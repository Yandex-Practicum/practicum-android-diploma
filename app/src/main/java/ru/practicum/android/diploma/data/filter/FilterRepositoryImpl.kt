package ru.practicum.android.diploma.data.filter

import ru.practicum.android.diploma.data.filter.local.LocalStorage
import ru.practicum.android.diploma.domain.filter.FilterRepository
import ru.practicum.android.diploma.domain.models.filter.Area
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.domain.models.filter.Industry

class FilterRepositoryImpl(
    private val localStorage: LocalStorage
) : FilterRepository {

    override fun setSalary(input: String) {
        localStorage.setSalary(input)
    }

    override fun getSalary(): String {
        return localStorage.getSalary()
    }

    override fun setSelectedCountry(country: Country?) {
        localStorage.setSelectedCountry(country)
    }

    override fun getSelectedCountry(): Country? {
        return localStorage.getSelectedCountry()
    }

    override fun setSelectedArea(area: Area?) {
        localStorage.setSelectedArea(area)
    }

    override fun getSelectedArea(): Area? {
        return localStorage.getSelectedArea()
    }

    override fun getSelectedIndustry(): Industry? {
        return localStorage.getSelectedIndustry()
    }

    override fun setSelectedIndustry(industry: Industry?) {
        return localStorage.setSelectedIndustry(industry)
    }
}
