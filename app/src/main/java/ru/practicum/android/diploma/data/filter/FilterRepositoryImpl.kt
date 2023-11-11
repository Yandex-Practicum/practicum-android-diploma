package ru.practicum.android.diploma.data.filter

import androidx.lifecycle.LiveData
import ru.practicum.android.diploma.data.filter.local.LocalStorage
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.domain.models.filter.FilterRepository

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

    override fun getSelectedCountryLiveData(): LiveData<Country?> {
        return localStorage.getSelectedCountryLiveData()
    }
}
