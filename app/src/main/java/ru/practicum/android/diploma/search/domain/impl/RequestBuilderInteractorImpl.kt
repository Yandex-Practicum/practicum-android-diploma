package ru.practicum.android.diploma.search.domain.impl

import ru.practicum.android.diploma.filters.industries.domain.models.Industry
import ru.practicum.android.diploma.search.data.model.SavedFilters
import ru.practicum.android.diploma.search.domain.api.RequestBuilderInteractor
import ru.practicum.android.diploma.search.domain.api.RequestBuilderRepository

class RequestBuilderInteractorImpl(private val requestBuilderRepository: RequestBuilderRepository) :
    RequestBuilderInteractor {
    override fun setText(text: String) {
        requestBuilderRepository.setText(text)
    }

    override fun setSalary(salary: String) {
        requestBuilderRepository.setSalary(salary)
    }

    override fun setIndustry(industry: Industry) {
        requestBuilderRepository.setIndustry(industry)
    }

    override fun setCurrency(currency: String) {
        requestBuilderRepository.setCurrency(currency)
    }

    override fun setIsShowWithSalary(isShowWithSalary: Boolean) {
        requestBuilderRepository.setIsShowWithSalary(isShowWithSalary)
    }

    override fun cleanIndustry() {
        requestBuilderRepository.cleanIndustry()
    }

    override fun getRequest(): HashMap<String, String> {
        return requestBuilderRepository.getRequest()
    }

    override fun getSavedFilters(): SavedFilters {
        return requestBuilderRepository.getSavedFilters()
    }

    override fun updateBufferedSavedFilters(newBufferedSavedFilters: SavedFilters) {
        requestBuilderRepository.updateBufferedSavedFilters(newBufferedSavedFilters)
    }

    override fun getBufferedSavedFilters(): SavedFilters {
        return requestBuilderRepository.getBufferedSavedFilters()
    }

    override fun saveFiltersToShared() {
        requestBuilderRepository.saveFiltersToShared()
    }

    override fun clearAllFilters() {
        requestBuilderRepository.clearAllFilters()
    }
}
