package ru.practicum.android.diploma.filters.base.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filters.areas.domain.api.AreaCashInteractor
import ru.practicum.android.diploma.search.data.model.SavedFilters
import ru.practicum.android.diploma.search.domain.api.RequestBuilderInteractor

class FilterSettingsViewModel(
    private val requestBuilderInteractor: RequestBuilderInteractor,
    private val areaCashInteractor: AreaCashInteractor
) : ViewModel() {

    private val baseFilterScreenState: MutableLiveData<FilterSettingsStateScreen> = MutableLiveData()
    val getBaseFiltersScreenState: LiveData<FilterSettingsStateScreen> = baseFilterScreenState
    private fun initFilters(): SavedFilters {
        return requestBuilderInteractor.getSavedFilters()
    }

    fun checkFilterFields() {
        val filters = initFilters()
        val areaName: String = if (!filters.savedArea?.name.isNullOrBlank()) {
            "${filters.savedArea?.parentName}, ${filters.savedArea?.name}"
        } else {
            filters.savedArea?.parentName ?: ""
        }

        baseFilterScreenState.value =
            FilterSettingsStateScreen.FilterSettings(
                areaName,
                filters.savedIndustry?.name ?: "",
                filters.savedSalary ?: "",
                filters.savedIsShowWithSalary ?: false
            )
    }

    fun clearArea() {
        areaCashInteractor.cleanArea()
    }

    fun clearIndustry() {
        requestBuilderInteractor.cleanIndustry()
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
