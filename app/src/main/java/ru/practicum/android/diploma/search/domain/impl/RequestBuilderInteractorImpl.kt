package ru.practicum.android.diploma.search.domain.impl

import ru.practicum.android.diploma.search.data.model.SavedFilters
import ru.practicum.android.diploma.search.domain.api.RequestBuilderInteractor
import ru.practicum.android.diploma.search.domain.api.RequestBuilderRepository

class RequestBuilderInteractorImpl(private val requestBuilderRepository: RequestBuilderRepository) :
    RequestBuilderInteractor {
    override fun setText(text: String) {
        requestBuilderRepository.setText(text)
    }

    override fun saveArea(areaId: String) {
        requestBuilderRepository.saveArea(areaId)
    }

    override fun saveSalary(salary: String) {
        requestBuilderRepository.saveSalary(salary)
    }

    override fun saveCurrency(currency: String) {
        requestBuilderRepository.saveCurrency(currency)
    }

    override fun saveIsShowWithSalary(isShowWithSalary: Boolean) {
        requestBuilderRepository.saveIsShowWithSalary(isShowWithSalary)
    }

    override fun getRequest(): HashMap<String, String> {
        return requestBuilderRepository.getRequest()
    }

    override fun getSavedFilters(): SavedFilters {
        return requestBuilderRepository.getSavedFilters()
    }
}
