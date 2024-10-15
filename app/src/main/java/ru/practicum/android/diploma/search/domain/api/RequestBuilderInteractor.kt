package ru.practicum.android.diploma.search.domain.api

import ru.practicum.android.diploma.search.data.model.SavedFilters

interface RequestBuilderInteractor {
    fun setText(text: String)
    fun saveArea(areaId: String)
    fun saveSalary(salary: String)
    fun saveCurrency(currency: String)
    fun saveIsShowWithSalary(isShowWithSalary: Boolean)
    fun getRequest(): HashMap<String, String>
    fun getSavedFilters(): SavedFilters
}
