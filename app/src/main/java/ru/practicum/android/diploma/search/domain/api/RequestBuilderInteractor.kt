package ru.practicum.android.diploma.search.domain.api

import ru.practicum.android.diploma.search.data.model.SavedFilters

interface RequestBuilderInteractor {
    fun setText(text: String)
    fun setArea(areaId: String)
    fun setSalary(salary: String)
    fun setIndustry(industryId: String)
    fun setCurrency(currency: String)
    fun setIsShowWithSalary(isShowWithSalary: Boolean)
    fun getRequest(): HashMap<String, String>
    fun getSavedFilters(): SavedFilters
}
