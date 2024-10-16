package ru.practicum.android.diploma.filters.base.presentation

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.search.data.model.SavedFilters
import ru.practicum.android.diploma.search.domain.api.RequestBuilderInteractor

class FilterSettingsViewModel(private val requestBuilderInteractor: RequestBuilderInteractor) : ViewModel() {

    fun initFilters(): SavedFilters {
        return TODO() // возвращает фильтры из sharedPrefs или пустые поля из SharedPrefs (.orEmpty)
    }

    fun setText(text: String) {
        // добавляет в запрос текст поиска
    }

    fun setArea(area: String) {
        // добавляет в запрос регион поиска и сохраняет его в sharedPrefs
    }

    fun setIndustry(industry: String) {
        // добавляет в запрос отрасль работы и сохраняет её в sharedPrefs
    }

    fun setSalary(salary: String) {
        // добавляет в запрос желаемую ЗП и сохраняет её в sharedPrefs
    }

    fun setIsShowWithSalary(isShowWithSalary: Boolean) {
        // добавляет в запрос переменную "показывать только с указанной ЗП" и сохраняет её в sharedPrefs
    }

    fun getRequest(): HashMap<String, String> {
        return TODO() // отдаёт собранный запрос
    }

}
