package ru.practicum.android.diploma.domain.models.filter

import androidx.lifecycle.LiveData

interface FilterRepository {
    fun setSalary(input: String)
    fun getSalary(): String

    fun setSelectedCountry(country: Country?)
    fun getSelectedCountry(): Country?

    fun getSelectedCountryLiveData(): LiveData<Country?>
}
