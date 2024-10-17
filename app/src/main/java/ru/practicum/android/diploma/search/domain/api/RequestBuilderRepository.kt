package ru.practicum.android.diploma.search.domain.api

import ru.practicum.android.diploma.filters.industries.domain.models.Industry
import ru.practicum.android.diploma.search.data.model.SavedFilters

interface RequestBuilderRepository {
    fun setText(text: String)
    fun setArea(areaId: String)
    fun setIndustry(industry: Industry)
    fun setSalary(salary: String)
    fun setCurrency(currency: String)
    fun setIsShowWithSalary(isShowWithSalary: Boolean)
    fun getRequest(): HashMap<String, String>
    fun getSavedFilters(): SavedFilters
}
