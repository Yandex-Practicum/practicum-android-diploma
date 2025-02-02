package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.common.sharedprefs.interactor.SharedPrefsInteractor
import ru.practicum.android.diploma.common.sharedprefs.models.City
import ru.practicum.android.diploma.common.sharedprefs.models.Country
import ru.practicum.android.diploma.common.sharedprefs.models.Filter
import ru.practicum.android.diploma.common.sharedprefs.models.Industry

class FilterSettingsViewModel(private val sharedPrefsInteractor: SharedPrefsInteractor) : ViewModel() {

    // Сохраняем полный фильтр
    fun saveFullFilter(country: Country, city: City, industry: Industry, salary: Int, withSalary: Boolean) {
        val filter = Filter(
            areaCountry = country,
            areaCity = city,
            industry = industry,
            salary = salary,
            withSalary = withSalary
        )
        sharedPrefsInteractor.saveFilter(filter)
    }

    // Обновляем только страну
    fun updateCountry(country: Country) {
        sharedPrefsInteractor.updateCountry(country)
    }

    // Обновляем только город
    fun updateCity(city: City) {
        sharedPrefsInteractor.updateCity(city)
    }

    // Обновляем только отрасль
    fun updateIndustry(industry: Industry) {
        sharedPrefsInteractor.updateIndustry(industry)
    }

    // Обновляем только зарплату
    fun updateSalary(salary: Int) {
        sharedPrefsInteractor.updateSalary(salary)
    }

    // Обновляем только флаг "с зарплатой"
    fun updateWithSalary(withSalary: Boolean) {
        sharedPrefsInteractor.updateWithSalary(withSalary)
    }

    // Получаем текущий фильтр
    fun getFilter() = sharedPrefsInteractor.getFilter()

    // Очищаем только страну
    fun clearCountry() {
        sharedPrefsInteractor.clearCountry()
    }

    // Очищаем только город
    fun clearCity() {
        sharedPrefsInteractor.clearCity()
    }
    // Очищаем только отрасль
    fun clearIndustry() {
        sharedPrefsInteractor.clearIndustry()
    }

    // Очищаем все поля
    fun clearAll() {
        sharedPrefsInteractor.clearAll()
    }
}
