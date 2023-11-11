package ru.practicum.android.diploma.data.filter.local

import androidx.lifecycle.LiveData
import ru.practicum.android.diploma.domain.models.filter.Country

interface LocalStorage {

    fun setSalary(salary: String)
    fun getSalary(): String

    fun setSelectedCountry(country: Country?)
    fun getSelectedCountry(): Country?
    fun getSelectedCountryLiveData(): LiveData<Country?>
}
