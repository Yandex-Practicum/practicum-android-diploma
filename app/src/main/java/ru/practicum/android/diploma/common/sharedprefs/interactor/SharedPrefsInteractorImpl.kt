package ru.practicum.android.diploma.common.sharedprefs.interactor

import ru.practicum.android.diploma.common.sharedprefs.models.City
import ru.practicum.android.diploma.common.sharedprefs.models.Country
import ru.practicum.android.diploma.common.sharedprefs.models.Filter
import ru.practicum.android.diploma.common.sharedprefs.models.Industry
import ru.practicum.android.diploma.common.sharedprefs.repository.SharedPrefsRepository

class SharedPrefsInteractorImpl(private val sharedPrefsRepository: SharedPrefsRepository) : SharedPrefsInteractor {

    override fun saveFilter(filter: Filter) {
        sharedPrefsRepository.saveFilter(filter)
    }

    override fun getFilter(): Filter {
        return sharedPrefsRepository.getFilter()
    }

    override fun updateCountry(country: Country) {
        sharedPrefsRepository.updateField(country = country)
    }

    override fun updateCity(city: City) {
        sharedPrefsRepository.updateField(city = city)
    }

    override fun updateIndustry(industry: Industry) {
        sharedPrefsRepository.updateField(industry = industry)
    }

    override fun updateSalary(salary: Int) {
        sharedPrefsRepository.updateField(salary = salary)
    }

    override fun updateWithSalary(withSalary: Boolean) {
        sharedPrefsRepository.updateField(withSalary = withSalary)
    }

    override fun clearCountry() {
        sharedPrefsRepository.clearField(country = true)
    }

    override fun clearCity() {
        sharedPrefsRepository.clearField(city = true)
    }

    override fun clearIndustry() {
        sharedPrefsRepository.clearField(industry = true)
    }

    override fun clearSalary() {
        sharedPrefsRepository.clearField(salary = true)
    }

    override fun clearWithSalary() {
        sharedPrefsRepository.clearField(withSalary = true)
    }

    override fun clearAll() {
        sharedPrefsRepository.clearAll()
    }
}
