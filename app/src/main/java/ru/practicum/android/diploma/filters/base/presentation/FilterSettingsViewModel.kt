package ru.practicum.android.diploma.filters.base.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.search.data.model.SavedFilters
import ru.practicum.android.diploma.search.domain.api.RequestBuilderInteractor

class FilterSettingsViewModel(private val requestBuilderInteractor: RequestBuilderInteractor) : ViewModel() {

    private val baseFilterScreenState: MutableLiveData<FilterSettingsStateScreen> = MutableLiveData()
    val getBaseFiltersScreenState: LiveData<FilterSettingsStateScreen> = baseFilterScreenState
    private fun initFilters(): SavedFilters {
        return requestBuilderInteractor.getSavedFilters()
    }

    private var salary: String = ""
    private var isShowWithSalary: Boolean = false

    fun checkFilterFields() {
        val filters = initFilters()
        baseFilterScreenState.value =
            FilterSettingsStateScreen.FilterSettings(
                filters.savedArea ?: "",
                filters.savedIndustry ?: "",
                filters.savedSalary ?: "",
                filters.savedIsShowWithSalary ?: false
            )
        salary = filters.savedSalary ?: ""
        isShowWithSalary = filters.savedIsShowWithSalary ?: false
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
        this.salary = salary
    }

    fun setIsShowWithSalary(isShowWithSalary: Boolean) {
        this.isShowWithSalary = isShowWithSalary
    }

    fun getRequest(): HashMap<String, String> {
        requestBuilderInteractor.setSalary(salary)
        requestBuilderInteractor.setIsShowWithSalary(isShowWithSalary)
        return TODO() // отдаёт собранный запрос
    }

}
