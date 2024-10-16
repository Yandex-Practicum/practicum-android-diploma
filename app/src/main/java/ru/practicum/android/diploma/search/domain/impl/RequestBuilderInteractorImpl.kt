package ru.practicum.android.diploma.search.domain.impl

import ru.practicum.android.diploma.search.data.model.SavedFilters
import ru.practicum.android.diploma.search.domain.api.RequestBuilderInteractor
import ru.practicum.android.diploma.search.domain.api.RequestBuilderRepository

class RequestBuilderInteractorImpl(private val requestBuilderRepository: RequestBuilderRepository) :
    RequestBuilderInteractor {
    override fun setText(text: String) {
        requestBuilderRepository.setText(text)
    }

    override fun setArea(areaId: String) {
        requestBuilderRepository.setArea(areaId)
    }

    override fun setSalary(salary: String) {
        requestBuilderRepository.setSalary(salary)
    }

    override fun setIndustry(industryId: String) {
        requestBuilderRepository.setIndustry(industryId)
    }

    override fun setCurrency(currency: String) {
        requestBuilderRepository.setCurrency(currency)
    }

    override fun setIsShowWithSalary(isShowWithSalary: Boolean) {
        requestBuilderRepository.setIsShowWithSalary(isShowWithSalary)
    }

    override fun getRequest(): HashMap<String, String> {
        return requestBuilderRepository.getRequest()
    }

    override fun getSavedFilters(): SavedFilters {
        return requestBuilderRepository.getSavedFilters()
    }
}
