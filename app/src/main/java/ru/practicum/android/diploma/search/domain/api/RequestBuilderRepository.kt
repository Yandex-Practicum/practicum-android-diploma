package ru.practicum.android.diploma.search.domain.api

import ru.practicum.android.diploma.search.data.model.SavedFilters

interface RequestBuilderRepository {
    fun setText(text: String)
    fun saveArea(areaId: String)
    fun saveIndustry(industryId: String)
    fun saveSalary(salary: String)
    fun saveCurrency(currency: String)
    fun saveIsShowWithSalary(isShowWithSalary: Boolean)
    fun getRequest(): HashMap<String, String>
    fun getSavedFilters(): SavedFilters
}
